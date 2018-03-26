package driver.modeAdapter;

import java.io.IOException;
import preprocessFiles.PreprocessFile;
import preprocessFiles.PreprocessNormal;

/**
 * This class was made to make comparison against a normal type easier 
 * and avoid making lots of classes. (This may be a violation of open close though)
 */
public class NormalVersusSpecificAttack extends ModeAdapterSpecificAttack{

   /**
    * Passes to the super constructor the PreprocessFile to be compared against normal.
    * When using super.getPreprocessFiles(), this also returns 
    * PreprocessNormal in addition to the variable passed in compareAgainstNormal
    * 
    * @param systemType 
    * @param specificAttack
    * @throws IOException 
    */
   public NormalVersusSpecificAttack(String systemType, PreprocessFile specificAttack)
           throws IOException {
      
      super(systemType, new PreprocessNormal(), specificAttack);
   }
}