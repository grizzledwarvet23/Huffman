import java.io.IOException;
import java.util.HashMap;

public class FrequencyCounter {
    //frequencies of bit sequences
    private HashMap<Integer, Integer> frequencies;
    private PriorityQueue314<TreeNode> queue;
    private TreeNode root;

    //map the original 8-bit chunk to the code from the path in huffman tree
    private HashMap<Integer, String> chunkCodes;


    public FrequencyCounter() {
        frequencies = new HashMap<>();
        queue = new PriorityQueue314<>();
        root = null;
    }

    //step 1
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

    //step 2
    public void buildQueue() {
        for(Integer key : frequencies.keySet()) {
            TreeNode node = new TreeNode(key, frequencies.get(key));
            queue.enqueue(node);
        }
    }

    //step 3-4
    public void buildTree() {
        while(queue.size() >= 2) {
            TreeNode leftChild = queue.dequeue();
            TreeNode rightChild = queue.dequeue();
            int newFreq = leftChild.getFrequency() + rightChild.getFrequency();
            TreeNode parent = new TreeNode(-1, newFreq); //-1 means no value here, doesn't matter
            parent.setLeft(leftChild);
            parent.setRight(rightChild);
            queue.enqueue(parent);
        }
        //one node left in the queue, root is assigned to it
        root = queue.dequeue();
    }

    public void mapChunkToCodes() {
        //let's do this recursively
        buildCode(root, "");
    }

    private void buildCode(TreeNode n, String codeSoFar) {
        if(n.isLeaf()) {
            chunkCodes.put(n.getValue(), codeSoFar);
        } else {
            buildCode(n.getLeft(), codeSoFar + "0");
            buildCode(n.getRight(), codeSoFar + "1");
        }
    }
    
    

}
