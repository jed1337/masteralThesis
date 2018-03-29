package driver;

import globalParameters.GlobalFeatureExtraction;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import preprocessFiles.preprocessAs.FormatAsArff;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public final class SingleModelTest {
   private final Instances rawInstances;
   private final Instances formattedInstances;
   private final Classifier model;

   private final int sourceIPIndex;
   private final int sourcePortIndex;
   private final int destIPIndex;
   private final int destPortIndex;
   private final int protocolIndex;

   public SingleModelTest(String rawInstancesPath, String modelPath) throws IOException, Exception{
      this.rawInstances    = UtilsInstances.getInstances(rawInstancesPath);
      this.sourceIPIndex   = UtilsInstances.getAttributeIndex(rawInstances,"src_ip");
      this.sourcePortIndex = UtilsInstances.getAttributeIndex(rawInstances,"src_port");
      this.destIPIndex     = UtilsInstances.getAttributeIndex(rawInstances,"dst_ip");
      this.destPortIndex   = UtilsInstances.getAttributeIndex(rawInstances,"dst_port");
      this.protocolIndex   = UtilsInstances.getAttributeIndex(rawInstances,"proto_number");

      FormatAsArff faa = new FormatAsArff(rawInstancesPath);
      faa.removeAttributes(
         GlobalFeatureExtraction.getInstance().getFeaturesToRemove()
      );

      this.formattedInstances = faa.getInstances();

      this.model = UtilsClssifiers.readModel(modelPath);
   }

   public void printClassAttribute(){
      for (int i = 0; i < this.rawInstances.classAttribute().numValues(); i++) {
         System.out.println(
            String.format("Index %d: %s",
               i,
               this.rawInstances.classAttribute().value(i)
            )
         );
      }
   }
   
   private void printTotalClassDistribution(double[] totalClassDistribution){
      final int numInstances = this.formattedInstances.numInstances();
      
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

   public void classify() throws Exception{
      final int classAttributeIndex   = this.formattedInstances.classAttribute().index();
      final Attribute classAttributes = this.formattedInstances.classAttribute();
      
      final double[] totalClassDistribution = new double[classAttributes.numValues()];

//      We can use a fore loop, but we need the index here
      for (int i = 0; i < this.formattedInstances.numInstances(); i++) {
         Instance formattedInstance = this.formattedInstances.get(i);
         String actualValue = formattedInstance.stringValue(classAttributeIndex);
         System.out.println("Actual class:    "+ actualValue);
         System.out.println("Predicted class: "+ getPredictedClass(classAttributes,this.model, formattedInstance));

         System.out.println("Information: "+basicIPDetails(this.rawInstances.get(i)));
         
         double[] classDistribution = this.model.distributionForInstance(formattedInstance);
         matrixAddition(totalClassDistribution, classDistribution);
         
         System.out.println(Arrays.toString(classDistribution));
         System.out.println();
      }
      printTotalClassDistribution(totalClassDistribution);
   }
   
   /**
    * Doesn't perform any checks to see if m1 and m2 have the same length. <br>
    * Adds the value of m2 to m1
    * @param m1 The first matrix
    * @param m2 The second matrix whose values are added to m1
    */
   private void matrixAddition(double[] m1, double[] m2){
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

   private String getPredictedClass(Attribute classAttributes, Classifier classifier, Instance toClassify) throws Exception{
      return classAttributes.value((int) classifier.classifyInstance(toClassify));
   }
}