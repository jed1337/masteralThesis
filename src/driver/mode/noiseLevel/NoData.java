package driver.mode.noiseLevel;

import java.util.ArrayList;
import preprocessFiles.PreprocessFile;

public class NoData extends NoiseLevel{
   private static NoData instance =null;
   
   private NoData(){}
   
   public static NoData getInstance(){
      if(NoData.instance == null){
         NoData.instance = new NoData();
      }
      return NoData.instance;
   }
   
   @Override
   public ArrayList<PreprocessFile> getPreprocessedFiles() {
      return super.pfAL;
   }
}