package globalParameters;

import featureExtraction.FeatureExtraction;

public class GlobalFeatureExtraction {
   private static FeatureExtraction instance=null;
   
   public static void setInstance(FeatureExtraction fe){
      if(GlobalFeatureExtraction.instance!=null){
         throw new IllegalArgumentException("The instance has already been set");
      }
      GlobalFeatureExtraction.instance = fe;
   }
   
   public static FeatureExtraction getInstance(){
      return GlobalFeatureExtraction.instance;
   }
}