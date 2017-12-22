package driver;

import classifier.ClassifierHolder;
import constants.ArffInstanceCount;
import constants.CharConstants;
import constants.DirectoryConstants;
import constants.FileNameConstants;
import customWeka.CustomEvaluation;
import driver.mode.Mode;
import driver.mode.Single;
import driver.mode.noiseLevel.NoNoise;
import driver.mode.noiseLevel.NoiseLevel;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.BiFunction;
import utils.Utils;
import utils.UtilsClssifiers;
import utils.UtilsInstances;
import weka.attributeSelection.ASEvaluation;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public final class Driver {
//<editor-fold defaultstate="collapsed" desc="System">
   public static void system() throws IOException, Exception {
      Instances validation = UtilsInstances.getInstances(
              "Results/TestTrainValidation/NormalOrAttack/Validation.arff");
      Instances hlValidation = UtilsInstances.getInstances(
              "Results/TestTrainValidation/HL/Validation.arff");
      Classifier normalClassifier = UtilsClssifiers.readModel(
              "Results/TestTrainValidation/NormalOrAttack/KNN.model");
      Classifier hlClassifier = UtilsClssifiers.readModel(
              "Results/TestTrainValidation/HL/KNN.model");

      for (int i = 0; i < validation.classAttribute().numValues(); i++) {
         System.out.println("Value " + i + " = " + validation.classAttribute()
                 .value(i));
      }
      for (int i = 0; i < validation.size(); i++) {

         String predictedValue = validation.classAttribute().value(
                 (int) normalClassifier.classifyInstance(validation.get(i)));
         String actualValue = validation.get(i).stringValue(26);

         if (predictedValue.equalsIgnoreCase("attack")) {
            predictedValue = hlValidation.classAttribute().value(
                    (int) hlClassifier.classifyInstance(validation.get(i)));
         }

         System.out.println(
                 i + ")\tPredictedValue: " + predictedValue
                 + "\t\tActualValue:\t" + actualValue
         );
      }
   }
//</editor-fold>
   
   //Temporary until we cana put the data in a database then custom get what we want
   private static final BiFunction<CustomEvaluation, ClassifierHolder, String> customEvaluation = (eval, ch)->{
      StringBuilder sb = new StringBuilder();
      sb.append(ch.getClassifierName()).append(": ");
      
      sb.append("Weighted Avg.");
      sb.append("(Prec: ");
      sb.append(Utils.doubleToString(eval.weightedPrecision(), 6, 4));
      sb.append("\tRecall: ");
      sb.append(Utils.doubleToString(eval.weightedRecall(), 6, 4));
      sb.append(")\t");

      final String[] classNames = eval.getClassNames();
      for (int i = 0; i < classNames.length; i++) {
         sb.append(classNames[i]);
         sb.append("(Prec: ");
         sb.append(Utils.doubleToString(eval.precision(i), 6, 4));
         sb.append("\tRecall: ");
         sb.append(Utils.doubleToString(eval.recall(i), 6, 4));
         sb.append(")\t");
      }

      sb.append(CharConstants.NEW_LINE);

      return sb.toString();
   };

   public static void main(String[] args) throws FileNotFoundException, IOException, Exception {
      final String folderPath = "Results/Three layer vs Single/No noise/J48/";
      final int instanceCount = ArffInstanceCount.HALVED;

      final NoiseLevel noiseLevel = NoNoise.getInstance();
      
      final WrapperSubsetEval wse = new WrapperSubsetEval();
      wse.setClassifier(new J48());
      wse.setFolds(5);
      
      systemTrain(new Single        (instanceCount, noiseLevel), wse, folderPath+"single/");
      
//      systemTrain(new HybridIsAttack(instanceCount, noiseLevel), wse, folderPath+"isAttack/");
//      systemTrain(new HybridDDoSType(instanceCount, NoNoise.getInstance()), wse, folderPath+"DDoS type/");
//      systemTrain(new SpecificHighrate(instanceCount, NoNoise.getInstance()), wse, folderPath+"specific highrate/");
//      systemTrain(new SpecificLowrate(instanceCount, NoNoise.getInstance()), wse, folderPath+"specific lowrate/");
      System.out.println("");
   }

   private static int[] systemTrain(final Mode mode, final ASEvaluation attributeEvaluator, final String folderPath)
           throws IOException, Exception {
      SystemTrain st = new SystemTrain(mode);
      st.setupTestTrainValidation();
      int[] result = st.applyFeatureSelection(attributeEvaluator, new BestFirst());
      st.customEvaluateClassifiers(
         Driver.customEvaluation,
         DirectoryConstants.RESULTS_DIR + FileNameConstants.COLLATED,
         folderPath //Doubles as the name inserted in collated (But this has no bearing on the path)
      );
      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath);
      return result;
   }
   
   private static void systemTrain(final Mode mode, final int[] selectedAttribtues, final String folderPath)
           throws IOException, Exception {
      SystemTrain st = new SystemTrain(mode);
      st.setupTestTrainValidation();
      st.applyFeatureSelection(selectedAttribtues);
      st.customEvaluateClassifiers(
              Driver.customEvaluation,
              DirectoryConstants.RESULTS_DIR + FileNameConstants.COLLATED,
              folderPath //Doubles as the name inserted in collated (But this has no bearing on the path)
      );
      Utils.duplicateDirectory(DirectoryConstants.FORMATTED_DIR, folderPath);
   }
}
