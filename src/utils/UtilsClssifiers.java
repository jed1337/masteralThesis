package utils;

import classifier.ClassifierHolder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.classifiers.evaluation.Evaluation;

public class UtilsClssifiers extends Utils {
   private UtilsClssifiers() {}

   public static Classifier readModel(String filename) throws Exception {
      return (Classifier) weka.core.SerializationHelper.read(filename);
   }

   public static void writeModel(Classifier classifier, Instances instances, String filename) throws Exception {
      writeModel(new ClassifierHolder(classifier, instances, filename));
   }

   public static void writeModel(ClassifierHolder ch) throws Exception {
      weka.core.SerializationHelper.write(ch.getModelName(), ch.getClassifier());
      System.out.println("Created the model of "+ch.getModelName());
   }

   public static void saveCrossValidationToFile(ArrayList<ClassifierHolder> chAL, int folds) throws Exception {
      for (ClassifierHolder ch : chAL) {
         saveCrossValidationToFile(ch, folds);
      }
   }

   public static void saveCrossValidationToFile(ClassifierHolder ch, int folds) throws Exception {
      Evaluation eval = new Evaluation(ch.getInstances());
      eval.crossValidateModel(ch.getClassifier(), ch.getInstances(), folds, new Random(1));

      saveStatistics(eval, ch, folds+" fold Cross validation");
   }

   public static void saveTestEvaluationToFile(ArrayList<ClassifierHolder> chAL, Instances testSet) throws Exception{
      for (ClassifierHolder ch : chAL) {
         saveTestEvaluationToFile(ch, testSet);
      }
   }

   public static void saveTestEvaluationToFile(ClassifierHolder ch, Instances testSet) throws Exception{
      Evaluation delegate = new Evaluation(ch.getInstances());
      delegate.evaluateModel(ch.getClassifier(), testSet);
      saveStatistics(delegate, ch, "Dedicated test set");
   }

   private static void saveStatistics(Evaluation eval, ClassifierHolder ch, String message) throws IOException, Exception {
      StringBuilder sb = new StringBuilder();

      sb.append("=== ").append(message).append(" ===\n");
      sb.append(eval.toSummaryString("=== Summary of "+ch.getClassifierName()+"===\n", false));
      sb.append(eval.toClassDetailsString("=== Detailed Accuracy By Class ===\n"));
      sb.append(eval.toMatrixString("=== Confusion Matrix ===\n"));

      System.out.println(sb);

      writeStringFile(ch.getResultName(), sb.toString());
   }
}
