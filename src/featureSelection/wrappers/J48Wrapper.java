package featureSelection.wrappers;

import weka.classifiers.trees.J48;

public final class J48Wrapper extends WrapperFeatureSelection{
   public J48Wrapper() {
      super(new J48(), "J48");
   }
}