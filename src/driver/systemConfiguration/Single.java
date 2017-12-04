package driver.systemConfiguration;

import constants.AttributeTypeConstants;
import driver.SystemTrain;
import java.io.IOException;
import weka.classifiers.Classifier;

public class Single extends SystemConfiguration {
   public Single(int count, NoiseLevel nl, Classifier featureSelector) throws IOException {
      super(count, nl, featureSelector);
   }
   
   @Override
   public void execute(String folderPath) throws IOException, Exception{
      new SystemTrain(
         folderPath + "Single/",
         super.setupFiles,
         AttributeTypeConstants.ATTRIBUTE_CLASS,
         "tcpFlood:highrate, udpFlood:highrate, httpFlood:highrate, slowBody:lowrate, slowHeaders:lowrate, slowRead:lowrate", 
         super.featureSelector
      );
   }

}
