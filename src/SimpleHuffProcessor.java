/*  Student information for assignment:
 *
 *  On OUR honor, Zayaan Rahman and Aman Rajeshkumar Modi, this programming assignment is OUR
 *  own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used:
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: zer235
 *  email address: zayaanr@utexas.edu
 *  Grader name: Pranav Chandupatla
 *
 *  Student 2
 *  UTEID: arm6433
 *  email address: aman.modi1763@gmail.com
 *
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.TreeMap;

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private HuffmanTree counter;


    /**
     * Preprocess data so that compression is possible ---
     * count characters/create tree/store state so that
     * a subsequent call to compress will work. The InputStream
     * is <em>not</em> a BitInputStream, so wrap it int one as needed.
     *
     * @param in           is the stream which could be subsequently compressed
     * @param headerFormat a constant from IHuffProcessor that determines what kind of
     *                     header to use, standard count format, standard tree format, or
     *                     possibly some format added in the future.
     * @return number of bits saved by compression or some other measure
     * Note, to determine the number of
     * bits saved, the number of bits written includes
     * ALL bits that will be written including the
     * magic number, the header format number, the header to
     * reproduce the tree, AND the actual data.
     * @throws IOException if an error occurs while reading from the input file.
     */
    public int preprocessCompress(InputStream in, int headerFormat) throws IOException {
        counter = new HuffmanTree();
        counter.countFrequencies(new BitInputStream(in));
        counter.buildQueue();
        counter.buildTree();
        counter.mapChunkToCodes();
        myViewer.update(counter.frequencies.toString());

        //original size: count sum of (frequencies * 8)
        //new size: count sum of (frequencies * string length of new shiz)
        myViewer.update(counter.chunkCodes.toString());

        int oldSize = 0;
        for (Integer key : counter.frequencies.keySet()) {
            oldSize += (BITS_PER_WORD * counter.frequencies.get(key));
        }

        oldSize -= 8; //PEOF was never included in the uncompressed file

        int newSize = 0;
        for (Integer key : counter.chunkCodes.keySet()) {
            newSize += (counter.frequencies.get(key) * counter.chunkCodes.get(key).length());
        }

        //Check whether headerFormat is SCF/STF and update new size accordingly
        newSize += BITS_PER_INT * 2;
        if (headerFormat == STORE_COUNTS) {
            newSize += (ALPH_SIZE * BITS_PER_INT);
        } else {
            newSize += (counter.leaves * 10) + counter.internalNodes + BITS_PER_INT;
        }

        myViewer.update("Old Size: " + oldSize);
        myViewer.update("New Size: " + newSize);

//        showString("Not working yet");
//        myViewer.update("Still not working");
//        throw new IOException("preprocess not implemented");
        return oldSize - newSize;
    }

    /**
     * Compresses input to output, where the same InputStream has
     * previously been pre-processed via <code>preprocessCompress</code>
     * storing state used by this call.
     * <br> pre: <code>preprocessCompress</code> must be called before this method
     *
     * @param in    is the stream being compressed (NOT a BitInputStream)
     * @param out   is bound to a file/stream to which bits are written
     *              for the compressed file (not a BitOutputStream)
     * @param force if this is true create the output file even if it is larger than the input file.
     *              If this is false do not create the output file if it is larger than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        BitInputStream inBitStream = new BitInputStream(in);
        BitOutputStream outBitStream = new BitOutputStream(out);
        // since preprocess has been called, our instance of counter has been constructed for our
        // desired file
        int read = inBitStream.read();
        int numBits = 0;
        while (read != -1) {
            String code = counter.chunkCodes.get(read);
            //write individual bits in the string
            for (int i = 0; i < code.length(); i++) {
                outBitStream.write(Integer.parseInt(code.substring(i, i + 1)));
                numBits++;
            }
            read = inBitStream.read(); //move to next chunk
        }
        return numBits;
        // throw new IOException("compress is not implemented");
    }

    /**
     * Uncompress a previously compressed stream in, writing the
     * uncompressed bits/data to out.
     *
     * @param in  is the previously compressed data (not a BitInputStream)
     * @param out is the uncompressed file/stream
     * @return the number of bits written to the uncompressed file/stream
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    public int uncompress(InputStream in, OutputStream out) throws IOException {

        throw new IOException("uncompress not implemented");
        //return 0;
    }

    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    private void showString(String s) {
        if (myViewer != null) {
            myViewer.update(s);
        }
    }

    private static class HuffmanTree {
        private TreeMap<Integer, Integer> frequencies; //frequencies of bit sequences

        private PriorityQueue314<TreeNode> queue; //A PQ that is used to develop HuffmanTree
        private TreeNode tree; //The final huffmanTree

        //map the original 8-bit chunk to the code from the path in huffman tree
        private HashMap<Integer, String> chunkCodes;

        private int internalNodes; //the total number of internal nodes in the huffman tree
        private int leaves; //the total number of leaves in the huffman tree

        /**
         * Constructor for the Huffman Tree
         */
        public HuffmanTree() {
            frequencies = new TreeMap<>();
            queue = new PriorityQueue314<>();
            tree = null;
            chunkCodes = new HashMap<>();
        }

        /**
         * Based on the BitInputStream of data, develop a TreeMap of frequencies
         * that stores the integer value of a particular bit-sequence as a key
         * and maps it to the frequency of how many times it has appeared in the
         * stream.
         *
         * @param stream The BitInputStream of data to read
         * @throws IOException
         */
        public void countFrequencies(BitInputStream stream) throws IOException {
            int nextSequence = stream.read();
            while (nextSequence != -1) {
                if (frequencies.get(nextSequence) == null) { //first time
                    frequencies.put(nextSequence, 1);
                } else {
                    frequencies.put(nextSequence, frequencies.get(nextSequence) + 1);
                }
                nextSequence = stream.read();
            }

            frequencies.put(256, 1); //The Pseudo-EOF value
        }

        /**
         * Generate a PriorityQueue that first creates a TreeNode for
         * every sequence-frequency pairing. Then, it queues them and prioritizes
         * the queue based on the frequency.
         */
        public void buildQueue() {
            for (Integer key : frequencies.keySet()) {
                queue.enqueue(new TreeNode(key, frequencies.get(key)));
            }
        }

        /**
         * Develops the HuffmanTree by dequeue two TreeNodes from the PriorityQueue.
         * Then, creates a Parent whose left child is the first dequeued node and the right
         * child is the second dequeued node. The Parent's frequency is set to the sum of the
         * frequencies of the dequeued nodes.
         * This process is repeated until we generate one TreeNode of all Huffman frequencies
         */
        public void buildTree() {
            while (queue.size() >= 2) {
                TreeNode leftChild = queue.dequeue();
                TreeNode rightChild = queue.dequeue();
                int newFreq = leftChild.getFrequency() + rightChild.getFrequency();

                //-1 means no value here, doesn't matter
                TreeNode parent = new TreeNode(-1, newFreq);
                parent.setLeft(leftChild);
                parent.setRight(rightChild);
                queue.enqueue(parent);
            }

            //one node left in the queue, tree is assigned to it
            tree = queue.dequeue();
        }

        /**
         * Once the Huffman Tree is created, generate a mapping of codes for each integer
         * value stored in the tree (Huffman tree).
         * To generate codes, we need to keep track of the directions we move.
         * 0 means we traversed along the left of the tree and 1 means to the right.
         * This is done until we hit a leaf, which indicates a character.
         */
        public void mapChunkToCodes() {
            buildCode(tree, "");
        }

        /**
         * A recursive helper method for mapChunkToCodes.
         * It generates the code as specified under the mapChunkToCodes method.
         *
         * @param node      The current node we are traversing on
         * @param codeSoFar The code we have generated so far for a specific value
         */
        private void buildCode(TreeNode node, String codeSoFar) {
            if (node.isLeaf()) {
                chunkCodes.put(node.getValue(), codeSoFar);
                leaves++;
            } else {
                buildCode(node.getLeft(), codeSoFar + "0");
                buildCode(node.getRight(), codeSoFar + "1");
                internalNodes++;
            }
        }


    }

}
