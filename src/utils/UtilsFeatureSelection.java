package utils;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class UtilsFeatureSelection {
   private UtilsFeatureSelection() {}

   public static Instances applyFeatureSelection(Instances instances, int[] selectedAttributes) throws Exception{
      Remove remove = new Remove();
      remove.setAttributeIndicesArray(selectedAttributes);
      remove.setInvertSelection(true);
      remove.setInputFormat(instances);

      return Filter.useFilter(instances, remove);
   }
}