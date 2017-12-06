package utils;

import classifier.ClassifierHolder;
import constants.CharConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.classifiers.evaluation.Evaluation;

public class UtilsClssifiers extends Utils {
   private UtilsClssifiers() {}

   public static Classifier readModel(String filename) throws Exception {
      return (Classifier) weka.core.SerializationHelper.read(filename);
   }
   
   public static void writeModel(ClassifierHolder ch) throws Exception {
      writeModel("", ch);
   }

   public static void writeModel(String directory, ClassifierHolder ch) throws Exception {
      Utils.makeFolders(ch.getFolderPath());
      weka.core.SerializationHelper.write(directory+ch.getModelName(), ch.getClassifier());
      System.out.println("Created the model of "+ch.getModelName());
   }

//   public static void saveCrossValidationToFile(ArrayList<ClassifierHolder> chAL, int folds) throws Exception {
//      for (ClassifierHolder ch : chAL) {
//         saveCrossValidationToFile(ch, folds);
//      }
//   }
//
//   public static Evaluation saveCrossValidationToFile(ClassifierHolder ch, int folds) throws Exception {
//      Instances ins = ch.getInstances();
//
//      Evaluation eval = new Evaluation(ins);
//      eval.crossValidateModel(ch.getClassifier(), ins, folds, new Random(1));
//
//      saveStatistics(eval, ch, folds+" fold Cross validation");
//      return eval;
//   }
//
//   public static void saveTestEvaluationToFile(ArrayList<ClassifierHolder> chAL, Instances testSet) throws Exception{
//      for (ClassifierHolder ch : chAL) {
//         saveTestEvaluationToFile(ch, testSet);
//      }
//   }
//
//   public static Evaluation saveTestEvaluationToFile(ClassifierHolder ch, Instances testSet) throws Exception{
//      Evaluation eval = new Evaluation(ch.getInstances());
//      eval.evaluateModel(ch.getClassifier(), testSet);
//
//      saveStatistics(eval, ch, "Dedicated test set");
//      return eval;
//   }
//
//   //Put add collated files here
//   private static void saveStatistics(Evaluation eval, ClassifierHolder ch, String message) throws IOException, Exception {
//      StringBuilder sb = new StringBuilder();
//
//      sb.append("=== ").append(message).append(" ===\n");
//      sb.append(eval.toSummaryString("=== Summary of "+ch.getClassifierName()+"===\n", false));
//      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
//      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));
//
//      System.out.println(sb);
//      
//      writeStringFile(ch.getResultName(), sb.toString());
//      
//      saveAccuracyToFile(eval, ch, "Accuracy.txt");
//   }
//
//   private static void saveAccuracyToFile(Evaluation eval, ClassifierHolder ch, String fileName) throws IOException, Exception {
//      Function<Evaluation, String> func = (fEval)->{
//         StringBuilder text = new StringBuilder();
//         text.append(ch.getClassifierName());
//         text.append(" = ");
//         text.append(Utils.doubleToString(fEval.pctCorrect(), 12, 4));
//         text.append(CharConstants.NEW_LINE);
//         return text.toString();
//      };
//      Utils.writeStringFile(
//         ch.getFolderPath()+fileName, 
//         func.apply(eval), 
//         true
//      );
//   }
}
