package featureSelection.wrappers;

import weka.classifiers.bayes.NaiveBayes;

public final class NBWrapper extends WrapperFeatureSelection{
   public NBWrapper() {
      super(new NaiveBayes(), "NB");
   }
}