package featureSelection;

import weka.core.Instances;

public class NoFeatureSelection implements FeatureSelection{
   private static NoFeatureSelection instance = null;

   private NoFeatureSelection() {}

   public static NoFeatureSelection getInstance(){
      if (NoFeatureSelection.instance == null) {
         NoFeatureSelection.instance = new NoFeatureSelection();
      }
      return NoFeatureSelection.instance;
   }

   @Override
   public void featureSelection(Instances trainSet) throws Exception {
      System.out.println("Not doing feature selection");
   }

   @Override
   public String getFSMethodName() {
      return "No feature selection";
   }
}