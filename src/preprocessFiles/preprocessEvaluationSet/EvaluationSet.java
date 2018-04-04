package preprocessFiles.preprocessEvaluationSet;

import weka.core.Instance;
import weka.core.Instances;

public final class EvaluationSet {
   private final String name;
   private final float splitValue;
   private Instances instances;

   public EvaluationSet(String name, float splitValue) {
      this(name, splitValue, null);
   }

   public EvaluationSet(String name, float splitValue, Instances instances) {
      this.name = name;
      this.splitValue = splitValue;
      this.instances = instances;
   }

   public void setInstances(Instances instances) {
      this.instances = instances;
   }

   public void addInstance(Instance instance) {
      this.instances.add(instance);
   }
   
   public String getName() {
      return name;
   }

   public float getSplitValue() {
      return splitValue;
   }

   public Instances getInstances() {
      return instances;
   }
}