package driver.mode.noiseLevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import preprocessFiles.PreprocessFile;

public final class NoData implements NoiseLevel{
   private static NoData instance =null;
   private NoData(){}
   
   public static NoData getInstance(){
      if(NoData.instance == null){
         NoData.instance = new NoData();
      }
      return NoData.instance;
   }
   
   @Override
   public List<PreprocessFile> getPreprocessedFiles() {
      final ArrayList<PreprocessFile> pfAL = new ArrayList<>();
      return Collections.unmodifiableList(pfAL);
   }
}