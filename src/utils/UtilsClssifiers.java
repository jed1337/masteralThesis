package utils;

import classifier.ClassifierHolder;
import weka.classifiers.Classifier;

public final class UtilsClssifiers{
   private UtilsClssifiers() {}

   public static Classifier readModel(String filename) throws Exception {
      return (Classifier) weka.core.SerializationHelper.read(filename);
   }
   
   public static void writeModel(ClassifierHolder ch) throws Exception {
      writeModel("", ch);
   }

   public static void writeModel(String directory, ClassifierHolder ch) throws Exception {
      final String path = directory+ch.getModelName();
      weka.core.SerializationHelper.write(path, ch.getClassifier());
      System.out.println("Created the model of "+ch.getModelName()+" at '"+path+"'");
   }
}