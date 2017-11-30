package driver;

import evaluate.Evaluate;
import setupFiles.SetupTestTrainValidation;
import evaluate.TrainTestValidation;
import constants.FileNameConstants;
import constants.PathConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import setupFiles.SetupFile;
import utils.Utils;
import utils.UtilsARFF;

public class SystemTrain {
   public SystemTrain
         (String folderPath, ArrayList<SetupFile> setupList, String attributeName, String toReplace) 
         throws IOException, Exception {
      
      for (SetupFile sfl: setupList) {
         sfl.setup();
         sfl.testRename(attributeName, toReplace);
         sfl.writeFile();
      }
      
      final String combinedPath = PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED;
      
      UtilsARFF.createArff(
         combinedPath,
         setupList.stream()
            .map(tl->tl.getFaa().getSavePath())
            .collect(Collectors.toList()),
         attributeName
      );
      
      //Feature selection
//      FeatureSelection.wrapperSelection(UtilsInstances.getInstances(combinedPath));

      SetupTestTrainValidation sttv = new SetupTestTrainValidation(combinedPath);
      sttv.setTrainTestValidationPaths(
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );

      Evaluate evaluate = new TrainTestValidation(
         folderPath,
         PathConstants.FORMATTED_DIR+FileNameConstants.TRAIN,
         PathConstants.FORMATTED_DIR+FileNameConstants.TEST,
         PathConstants.FORMATTED_DIR+FileNameConstants.VALIDATION
      );
      evaluate.buildModel();
      evaluate.writeModel();
      evaluate.evaluateModel();
      
      Utils.duplicateFolder(PathConstants.FORMATTED_DIR, evaluate.getFullFolderPath());
   }
}