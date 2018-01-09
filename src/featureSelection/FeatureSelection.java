package featureSelection;

import weka.core.Instances;

public interface FeatureSelection {
   public abstract void featureSelection(Instances trainSet) throws Exception;
   public abstract String getFSMethodName();
}
