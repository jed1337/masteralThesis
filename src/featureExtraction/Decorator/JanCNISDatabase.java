package featureExtraction.Decorator;

import featureExtraction.FeatureExtraction;

public class JanCNISDatabase extends ExtractionDBPathDecorator{
   public JanCNISDatabase(FeatureExtraction fe) {
      super(
         fe, 
         "Jan CNIS/"
      );
   }
}