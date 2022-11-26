import java.io.IOException;
import java.util.HashMap;

public class FrequencyCounter {
    //frequencies of bit sequences
    private HashMap<Integer, Integer> frequencies;
    private PriorityQueue314<TreeNode> queue;

    public FrequencyCounter() {
        frequencies = new HashMap<>();
        queue = new PriorityQueue314<>();
    }

    public void countFrequencies(BitInputStream stream) throws IOException {
        int nextSequence = stream.read();
        while(nextSequence > -1) {
            if(frequencies.get(nextSequence) == null) { //first time
                frequencies.put(nextSequence, 1);
            } else {
                frequencies.put(nextSequence, frequencies.get(nextSequence) + 1);
            }
        }
    }
    
    public void buildQueue() {
        for(Integer key : frequencies.keySet()) {
            TreeNode node = new TreeNode(key, frequencies.get(key));
            queue.enqueue(node);
        }
    }

    public void buildTree() {
        while(queue.size() >= 2) {
            TreeNode leftChild = queue.dequeue();
            TreeNode rightChild = queue.dequeue();
            int newFreq = leftChild.getFrequency() + rightChild.getFrequency();
            TreeNode parent = new TreeNode(-1, newFreq);
            parent.setLeft(leftChild);
            parent.setRight(rightChild);
            queue.enqueue(parent);
        }
    }
    
    

}
