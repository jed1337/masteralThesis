package driver.liveSystem.modelTest;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import preprocessFiles.preprocessAs.FormatAsArff;
import utils.Utils;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class ModelTest{
   /*** Used to pass the classification to a different ModelTest*/
   private final ArrayList<ConnectModelTest> cmtAL;

   private final Instances rawInstances;
   private final Instances formattedInstances;
   private final int classAttributeIndex;
   private final Attribute classAttribute;
   private final Classifier model;

   private final int sourceIPIndex;
   private final int sourcePortIndex;
   private final int destIPIndex;
   private final int destPortIndex;
   private final int protocolIndex;

   public ModelTest(Instances rawInstances, Classifier model) throws IOException, Exception{
      this.cmtAL = new ArrayList<>();

      this.rawInstances = rawInstances;

      this.sourceIPIndex = UtilsInstances.getAttributeIndex(this.rawInstances, "src_ip");
      this.sourcePortIndex = UtilsInstances.getAttributeIndex(this.rawInstances, "src_port");
      this.destIPIndex = UtilsInstances.getAttributeIndex(this.rawInstances, "dst_ip");
      this.destPortIndex = UtilsInstances.getAttributeIndex(this.rawInstances, "dst_port");
      this.protocolIndex = UtilsInstances.getAttributeIndex(this.rawInstances, "proto_number");


      FormatAsArff faa = new FormatAsArff(rawInstances);
      faa.removeAttributes(
         GlobalFeatureExtraction.getInstance().getFeaturesToRemove()
      );

      this.formattedInstances = faa.getInstances();
      classAttributeIndex   = this.formattedInstances.classAttribute().index();
      classAttribute = this.formattedInstances.classAttribute();

      this.model = model;
   }

   /**
    * Classify all instances
    * @throws Exception
    */
   public void classify() throws Exception{
      final double[] totalClassDistribution = new double[classAttribute.numValues()];

//      We can use a fore loop, but we need the index here
      for (int i = 0; i < this.formattedInstances.numInstances(); i++) {
         double[] classDistribution = classify(i);
         matrixAddition(totalClassDistribution, classDistribution);

         System.out.println(Arrays.toString(classDistribution));
         System.out.println();
      }

      printTotalClassDistribution(totalClassDistribution);
   }

   /**
    * Only classify the instance at the given index
    * Passes the instance to the first ConnectModelTest it matches
    * @param index
    * @return Th class distribution
    * @throws Exception
    */
   public double[] classify(int index) throws Exception {
      final Instance formattedInstance = this.formattedInstances.get(index);
      final String actualValue = formattedInstance.stringValue(this.classAttributeIndex);
      final String predictedClass = getPredictedClass(this.classAttribute, this.model, formattedInstance);

      displayClassificationResults(actualValue, predictedClass);

      for (ConnectModelTest cmt : this.cmtAL) {
         if(Utils.arrayContains(cmt.getNominalValues(), predictedClass)){
            return cmt.getConnectModelTest().classify(index);
         }
      }

      System.out.println("Information: "+basicIPDetails(this.rawInstances.get(index)));

      return this.model.distributionForInstance(formattedInstance);
   }

   /**
    * If the value of attribute is within attributeValues (caseInsensitive),
    * pass the classification to nextModeltest
    * throws an error if not nominal
    * @param cmt
    */
   public void addConnectModel(ConnectModelTest cmt){
      this.cmtAL.add(cmt);
   }

   /**
    * Print the class attributes along with which index corresponds
    * to which class attribute
    */
   public void printClassAttribute(){
      System.out.println("Class attributes per index");
      for (int i = 0; i < this.rawInstances.classAttribute().numValues(); i++) {
         System.out.println(
            String.format("Index %d: %s",
               i,
               this.rawInstances.classAttribute().value(i)
            )
         );
      }
   }

   /**
    * Print the percentage that a certain class was classified. 
    * This also includes the class index corresponding to it
    * @param totalClassDistribution 
    */
   private void printTotalClassDistribution(double[] totalClassDistribution){
      final int numInstances = this.formattedInstances.numInstances();

      System.out.println("Total class distribution:");
      for (int i = 0; i < this.rawInstances.classAttribute().numValues(); i++) {
         System.out.println(
            //"%%" Means a literal "%" symbol
            String.format("Index %d: %s:\t %.4f%%",
               i,
               this.rawInstances.classAttribute().value(i),
               (totalClassDistribution[i]/numInstances)*100
            )
         );
      }
   }

   /**
    * Doesn't perform any checks to see if m1 and m2 have the same length. <br>
    * Adds the value of m2 to m1
    * @param m1 The first matrix
    * @param m2 The second matrix whose values are added to m1
    */
   private void matrixAddition(double[] m1, double[] m2){
      if(m1.length!=m2.length){
         throw new InputMismatchException(
            String.format(
               "The matrices aren't the same size.\n"
                  + "matrix1.length: %d, matrix2.length: %d",
               m1.length,
               m2.length
            )
         );
      }
      
      for (int i = 0; i < m1.length; i++) {
         m1[i]+=m2[i];
      }
   }

   /**
    * Uses the rawInstance to get the information from since the formattedInstances
    * have had the source and destination port and IP, and protocol removed.
    * @param rawInstance
    * @return A string representation of the basic IP details
    */
   private String basicIPDetails(Instance rawInstance){
//      The %.0f means that we want 0 digits after the decimal point
      return String.format("Source: %s:%.0f, Destination: %s:%.0f, protocolID: %.0f",
         rawInstance.stringValue(this.sourceIPIndex),
         rawInstance.value(this.sourcePortIndex),
         rawInstance.stringValue(this.destIPIndex),
         rawInstance.value(this.destPortIndex),
         rawInstance.value(this.protocolIndex)
      );
   }

   private String getPredictedClass(Attribute classAttribute, Classifier classifier, Instance toClassify) throws Exception{
      return classAttribute.value((int) classifier.classifyInstance(toClassify));
   }

   private void displayClassificationResults(final String actualValue, final String predictedClass) {
      System.out.println("Actual class:    "+ actualValue);
      System.out.println("Predicted class: "+ predictedClass);
   }
}
