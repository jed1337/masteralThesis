package classifier;

import weka.attributeSelection.ASSearch;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.WrapperSubsetEval;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class FeatureSelection {
   private FeatureSelection() {}
   /**
    * Uses wrapper method.
    * Classifier = J48
    * Evaluation = BestFirst
    * 
    * @param data         The instances to classifier
    * @return Instances with reduced features
    *
    * @throws Exception
    */
   public static Instances wrapperSelection(Instances data) throws Exception {
      return wrapperSelection(data, new J48(), new BestFirst());
   }
   
   /**
    * Uses wrapper method
    *
    * @param data         The instances to classifier
    * @param classifier   Which classifier to use as evaluation
    * @param searchMethod Which search method to use (Best first, Greedy
    *                     Stepwise). Ranker doesn't work
    * @return Instances with reduced features
    *
    * @throws Exception
    */

   public static Instances wrapperSelection(Instances data, Classifier classifier, ASSearch searchMethod) throws Exception {
      data.setClassIndex(data.numAttributes() - 1);

      AttributeSelection as = new AttributeSelection();
      WrapperSubsetEval attEval = new WrapperSubsetEval();

      attEval.buildEvaluator(data);
      attEval.setClassifier(classifier);
      attEval.setFolds(5);

      as.setEvaluator(attEval);
      as.setSearch(searchMethod);
      as.SelectAttributes(data);
      
      System.out.println(as.toResultsString());

      Remove remove = new Remove();
      remove.setAttributeIndicesArray(as.selectedAttributes());
      remove.setInputFormat(data);
      return Filter.useFilter(data, remove);
   }
}