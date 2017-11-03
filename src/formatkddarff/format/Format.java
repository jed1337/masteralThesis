package formatkddarff.format;

import java.io.IOException;

public abstract class Format {
   protected String saveFileFullPath="";

   public String getSaveFileFullPath() throws IOException {
      if(saveFileFullPath.isEmpty()){
         throw new IOException("No save file name given");
      }
      return saveFileFullPath;
   }

   public void setSaveFileFullPath(String saveFileFullPath) {
      this.saveFileFullPath = saveFileFullPath;
   }
}