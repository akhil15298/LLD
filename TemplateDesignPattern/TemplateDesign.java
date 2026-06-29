package TemplateDesignPattern;

abstract class Model{
    public final void trainpipeline(String data){
        loaddata(data);
        preprocessData();
        trainModel();      
        evaluateModel();   
        saveModel();       
    }

    protected void loaddata(String path) {
        System.out.println("[Common] Loading dataset from " + path);
        // e.g., read CSV, images, etc.
    }

    protected void preprocessData() {
        System.out.println("[Common] Splitting into train/test and normalizing");
    }

    protected abstract void trainModel();
    protected abstract void evaluateModel();

    // Provide a default save, but subclasses can override if needed
    protected void saveModel() {
        System.out.println("[Common] Saving model to disk as default format");
    }
}

class NeuralNetworkTrainer extends Model {
    @Override
    protected void trainModel() {
        System.out.println("[NeuralNet] Training Neural Network for 100 epochs");
        // pseudo-code: forward/backward passes, gradient descent...
    }

    @Override
    protected void evaluateModel() {
        System.out.println("[NeuralNet] Evaluating accuracy and loss on validation set");
    }

    @Override
    protected void saveModel() {
        System.out.println("[NeuralNet] Serializing network weights to .h5 file");
    }
}

class DecisionTreeTrainer extends Model {
    // Use the default preprocessData() (train/test split + normalize)

    @Override
    protected void trainModel() {
        System.out.println("[DecisionTree] Building decision tree with max_depth=5");
        // pseudo-code: recursive splitting on features...
    }

    @Override
    protected void evaluateModel() {
        System.out.println("[DecisionTree] Computing classification report (precision/recall)");
    }
    // use the default saveModel()
}


public class TemplateDesign {
    public static void main(String[] args) {
        System.out.println("=== Neural Network Training ===");
        Model nnTrainer = new NeuralNetworkTrainer();
        nnTrainer.trainpipeline("data/images/");

        System.out.println("\n=== Decision Tree Training ===");
        Model dtTrainer = new DecisionTreeTrainer();
        dtTrainer.trainpipeline("data/iris.csv");
    }
}
