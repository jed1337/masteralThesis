package featureExtraction.Decorator;

import featureExtraction.FeatureExtraction;

public class Feb2CNISDatabase extends ExtractionDBPathDecorator{
   public Feb2CNISDatabase(FeatureExtraction fe) {
      super(
         fe, 
         "Feb 2 CNIS/"
      );
   }
}