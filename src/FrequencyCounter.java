import java.io.IOException;
import java.util.HashMap;
import java.util.TreeMap;

public class FrequencyCounter {
    //frequencies of bit sequences
    public TreeMap<Integer, Integer> frequencies;
    public PriorityQueue314<TreeNode> queue;
    public TreeNode root;

    //map the original 8-bit chunk to the code from the path in huffman tree
    public HashMap<Integer, String> chunkCodes;


    public FrequencyCounter() {
        frequencies = new TreeMap<>();
        queue = new PriorityQueue314<>();
        root = null;
        chunkCodes = new HashMap<>();
    }

    //step 1
    public void countFrequencies(BitInputStream stream) throws IOException {
        int nextSequence = stream.read();
        while(nextSequence != -1) {
            if(frequencies.get(nextSequence) == null) { //first time
                frequencies.put(nextSequence, 1);
            } else {
                frequencies.put(nextSequence, frequencies.get(nextSequence) + 1);
            }
            nextSequence = stream.read();
        }
        frequencies.put(256, 1); //The Pseudo-EOF value

    }

    //step 2
    public void buildQueue() {
        for(Integer key : frequencies.keySet()) {
            queue.enqueue(new TreeNode(key, frequencies.get(key)));
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
