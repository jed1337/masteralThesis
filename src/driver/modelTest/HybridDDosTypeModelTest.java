//package driver.modelTest;
//
//import java.io.IOException;
//import weka.classifiers.Classifier;
//import weka.core.Instance;
//import weka.core.Instances;
//
//public class HybridDDosTypeModelTest extends ModelTest{
//   public HybridDDosTypeModelTest(Instances rawInstances, Classifier model)
//      throws IOException, Exception {
//      super(rawInstances, model);
//   }
//   
//   @Override
//   public void classify() throws Exception {
//      throw new UnsupportedOperationException(
//         "Not supported since this classifier is meant to be called by hybridIsAttackModelTest."
//      );
//   }
//
//   @Override
//   public double[] classify(int index) throws Exception {
//      final Instance formattedInstance = super.formattedInstances.get(index);
//      final String actualValue = formattedInstance.stringValue(super.classAttributeIndex);
//      final String predictedClass = getPredictedClass(super.classAttribute, super.model, formattedInstance);
//      
//      displayClassificationResults(actualValue, predictedClass);
//      System.out.println("Information: "+basicIPDetails(this.rawInstances.get(index)));
//      
//      return super.model.distributionForInstance(formattedInstance);
//   }
//}