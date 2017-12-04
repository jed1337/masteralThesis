package driver;

import featureSelection.FeatureSelection;
import evaluate.Evaluate;
import preprocessFiles.SetupTestTrainValidation;
import evaluate.TrainTestValidation;
import constants.FileNameConstants;
import constants.PathConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import preprocessFiles.PreprocessFile;
import utils.Utils;
import utils.UtilsARFF;
import utils.UtilsInstances;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

public class SystemTrain {
   public SystemTrain
         (String folderPath, ArrayList<PreprocessFile> setupList, String attributeName, String toReplace, Classifier featureSelector) 
         throws IOException, Exception {
      final String combinedPath = PathConstants.FORMATTED_DIR+FileNameConstants.COMBINED;
      
      for (PreprocessFile sfl: setupList) {
         sfl.setUp();
         sfl.testRename(attributeName, toReplace);
         sfl.writeFile();
      }
      
      UtilsARFF.createArff(
         combinedPath,
         setupList.stream()
            .map(tl->tl.getFaa().getSavePath())
            .collect(Collectors.toList()),
         attributeName
      );
      
      //Feature selection
      //Write it to a file
      Utils.writeStringFile(
         combinedPath,
         FeatureSelection.wrapperSelection(
            UtilsInstances.getInstances(combinedPath),
            featureSelector
         ).toString()
      );
      
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