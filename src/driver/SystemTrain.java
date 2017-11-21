package driver;

import classify.Classify;
import classify.TempHolder;
import classify.TrainTestValidation;
import constants.FileNameConstants;
import constants.PathConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import train.Train;
import utils.Utils;

public class SystemTrain {
   public SystemTrain
        (String folderPath, ArrayList<Train> trainList, HashMap<String, String>... modifications) throws Exception {
      
      for (Train train : trainList) {
         train.setup();
         train.writeFile();
         train.replaceAllStrings(modifications);
      }

      Utils.createArff(
         PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED,
         trainList.stream().
            map(tl->tl.getFaa().getSavePath())
            .collect(Collectors.toList())
      );

      TempHolder th = new TempHolder(PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED);
      th.setTrainTestValidation(
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );

      Classify classify = new TrainTestValidation(
         folderPath,
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );
      classify.buildModel();
      classify.writeModel();
      classify.evaluateModel();
      
      Utils.duplicateFolder(PathConstants.FORMATTED_DIR, classify.getFullFolderPath());
//      final File directory = new File(PathConstants.FORMATTED_DIR);
//      for (final File file : directory.listFiles()) {
//         Utils.duplicateFile(file.getAbsolutePath(), classify.getFullFolderPath()+file.getName());
//      }
   }
}