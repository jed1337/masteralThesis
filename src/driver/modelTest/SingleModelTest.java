//package driver.modelTest;
//
//import java.io.IOException;
//import java.util.Arrays;
//import weka.classifiers.Classifier;
//import weka.core.Instance;
//import weka.core.Instances;
//
//public final class SingleModelTest extends ModelTest{
//   public SingleModelTest(Instances rawInstances, Classifier model) 
//      throws IOException,Exception {
//      super(rawInstances, model);
//   }
//
//   @Override
//   public void classify() throws Exception{
//      final double[] totalClassDistribution = new double[classAttribute.numValues()];
//
////      We can use a fore loop, but we need the index here
//      for (int i = 0; i < super.formattedInstances.numInstances(); i++) {
//         double[] classDistribution = classify(i);
//         matrixAddition(totalClassDistribution, classDistribution);
//
//         System.out.println(Arrays.toString(classDistribution));
//         System.out.println();
//      }
//      
//      printTotalClassDistribution(totalClassDistribution);
//   }
//
//   @Override
//   public double[] classify(int index) throws Exception {
//      final Instance formattedInstance = super.formattedInstances.get(index);
//      final String actualValue = formattedInstance.stringValue(super.classAttributeIndex);
//      final String predictedClass = getPredictedClass(super.classAttribute, super.model, formattedInstance);
//      
//      displayClassificationResults(actualValue, predictedClass);
//
//      System.out.println("Information: "+basicIPDetails(super.rawInstances.get(index)));
//
//      return super.model.distributionForInstance(formattedInstance);
//   }
//}