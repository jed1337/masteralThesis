package featureExtraction.Decorator;

import featureExtraction.FeatureExtraction;

public class FinalDatabase extends ExtractionDBPathDecorator{
    public FinalDatabase(FeatureExtraction fe) {
        super(
            fe, 
            "Final/"
        );
    }
    
}
