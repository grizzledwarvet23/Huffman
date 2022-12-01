/*  Student information for assignment:
 *
 *  On OUR honor, Zayaan Rahman and Aman Rajeshkumar Modi, this programming assignment is OUR
 *  own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used: 0
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: zer235
 *  email address: zayaanr@utexas.edu
 *  Grader name: Pranav Chandupatla
 *
 *  Student 2
 *  UTEID: arm6433
 *  email address: aman.modi1763@gmail.com
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.TreeMap;


public class SimpleHuffProcessor implements IHuffProcessor {

    private final static int LEAF_SIZE = 10;
    private final static int BIT_FOR_INTERNAL_NODE = 0;

    private static final int BIT_FOR_LEFTDIR = 0;
    private static final int BIT_FOR_RIGHTDIR = 1;

    private final static int BIT_FOR_LEAF = 1;

    private final static int DECIMAL_TO_PERCENT = 100;

    private IHuffViewer myViewer;
    private HuffmanTree counter; //Huffman Tree used in encoding

    private int header;
    private int newSize;
    private int oldSize;

    private static final int BITS_PER_INT = 32;


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
        //Initialize
        newSize = 0;
        oldSize = 0;
        header = headerFormat;

        counter = new HuffmanTree();
        counter.generateHuffmanTree(new BitInputStream(in));

        //Calculate the original size before compression
        for (Integer key : counter.frequencies.keySet()) {
            oldSize += (BITS_PER_WORD * counter.frequencies.get(key));
        }

        oldSize -= BITS_PER_WORD; //PEOF was never included in the uncompressed file

        //Calculate the new size before compression
        for (Integer key : counter.chunkCodes.keySet()) {
            newSize += (counter.frequencies.get(key) * counter.chunkCodes.get(key).length());
        }

        //Check whether headerFormat is SCF/STF and update new size accordingly
        newSize += BITS_PER_INT * 2;
        if (headerFormat == STORE_COUNTS) {
            newSize += (ALPH_SIZE * BITS_PER_INT);
        } else if (headerFormat == STORE_TREE) {
            newSize += (counter.leaves * LEAF_SIZE) + counter.internalNodes + BITS_PER_INT;
        } else {
            throw new IllegalArgumentException("Invalid Header Format Data type.");
        }

        displayPreprocessMessages();

        return oldSize - newSize;
    }

    /**
     * Display messages to update the user after preprocess compress
     */
    private void displayPreprocessMessages() {
        myViewer.update("Successful Preprocessing...");
        myViewer.update("Old Bits of the file, before compression " + oldSize);
        myViewer.update("New Bits of the file, AFTER compression " + newSize);
        myViewer.update("The amount of bits saved from compression " + (oldSize - newSize));
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
     * @param force if this is true create the output file even if
     *              it is larger than the input file.
     *              If this is false do not create the output file if it is larger
     *              than the input file.
     * @return the number of bits written.
     * @throws IOException if an error occurs while reading from the input file or
     *                     writing to the output file.
     */
    public int compress(InputStream in, OutputStream out, boolean force) throws IOException {
        if (force || (newSize < oldSize)) {
            //Write data into compressed file
            writeCompressData(new BitInputStream(in), new BitOutputStream(out));
            displayCompressionMessages();
        } else {
            myViewer.showError("Compressed file has " + (newSize - oldSize) + " more bits " +
                    "than the uncompressed file. Select force compression option to compress.");
        }

        return (force || (newSize < oldSize)) ? newSize : 0;
    }

    /**
     * Write out the compressed data into outBitStream, based on the Huffman Code Tree
     * @param inBitStream The data to read in from
     * @param outBitStream The data to write the compressed data from
     */
    private void writeCompressData(BitInputStream inBitStream, BitOutputStream outBitStream)
                                                                throws IOException {
        //Step 1: Write the magic number
        outBitStream.writeBits(BITS_PER_INT, MAGIC_NUMBER);

        //Step 2: Write the header format
        outBitStream.writeBits(BITS_PER_INT, header);

        //Step 3: Write the STF header data
        if (header == STORE_TREE) {
            outBitStream.writeBits(BITS_PER_INT, counter.internalNodes +
                    (counter.leaves * LEAF_SIZE));
            writeHeaderDataSTF(outBitStream, counter.tree);
        } else if (header == STORE_COUNTS) {
            writeHeaderDataSCF(outBitStream);
        } else {
            throw new IllegalArgumentException("Invalid Header Format Data type.");
        }

        // Step 4: Using the chunk codes, write out the data
        int read;
        while ((read = inBitStream.read()) != -1) {
            //write individual bits in the string
            convertSequence(counter.chunkCodes.get(read), outBitStream);
        }

        //Step 5: Add the PEOF value
        convertSequence(counter.chunkCodes.get(ALPH_SIZE), outBitStream);
        outBitStream.close();
    }

    /**
     * Display messages to update the user after compression process
     */
    private void displayCompressionMessages() {
        myViewer.update("Successful Compressing...");
        myViewer.update("The amount of bits written into compressed file " + newSize);
        int percentCompress = ((newSize - oldSize) / oldSize) * DECIMAL_TO_PERCENT;
        myViewer.update("The % of compression " + percentCompress);

        myViewer.update("CODES RESULTING FROM HUFFMAN TREE: ");

        for (Integer key : counter.chunkCodes.keySet()) {
            myViewer.update(key + " = " + counter.chunkCodes.get(key));
        }
    }
    /**
     * Write out the header data for the STF compression method
     * This takes places during compression, while compressing STF files.
     * This method uses a pre-order traversal method to write out the header data
     * If the current node is an internal node, write out a 0 and traverse through
     * the other nodes. Else, if the current node is a lead, write out a 1, and
     * the value of leaf (in 9 bits).
     * pre: none
     * post: The outBitStream now contains header data for STF compression
     * @param outBitStream The outBitStream to write the data on
     * @param node The current node we are traversing on
     */
    private void writeHeaderDataSTF(BitOutputStream outBitStream, TreeNode node) {
        if (node.isLeaf()) {
            outBitStream.writeBits(1, BIT_FOR_LEAF);
            outBitStream.writeBits(BITS_PER_WORD + 1, node.getValue());
        } else {
            outBitStream.writeBits(1, BIT_FOR_INTERNAL_NODE);
            writeHeaderDataSTF(outBitStream, node.getLeft());
            writeHeaderDataSTF(outBitStream, node.getRight());
        }
    }

    /**
     * Writes out the header data for the SCF compression method.
     * This takes places during compression, while compressing for SCF format.
     * It loops through all the values for 8 bits and based on the frequency
     * the value appears on the Huffman Tree, it writes out that many bits.
     * pre: none
     * post: The outBitStream now contains header data for SCF compression
     * @param outBitStream The outBitStream to write the data on
     */
    private void writeHeaderDataSCF(BitOutputStream outBitStream) {
        for (int i = 0; i < IHuffConstants.ALPH_SIZE; i++){
            int valToAdd;
            if(counter.frequencies.get(i) == null){
                valToAdd = 0; //This value doesn't exist in Huffman tree, 0 frequencies
            } else {
                valToAdd = counter.frequencies.get(i);
            }
            outBitStream.writeBits(BITS_PER_INT,valToAdd);
        }
    }

    /**
     * Given a code for a particular encoded Huffman value, write out
     * its bits in the outBitStream.
     * pre: none, handled by the method calling this method
     * post: The outBitStream now contains the code from the String code in bits
     * @param code The code to write on the outBitStream
     * @param outBitStream The outputStream to write data on
     */
    private void convertSequence(String code, BitOutputStream outBitStream) {
        for (int i = 0; i < code.length(); i++) {
            outBitStream.writeBits(1, Integer.parseInt(code.substring(i, i + 1)));
        }
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
        BitOutputStream newStream = new BitOutputStream(out);
        BitInputStream inStream = new BitInputStream(in);

        TreeNode treeRoot = null;

        int magicNum = inStream.readBits(BITS_PER_INT); //magic num at beginning
        if (magicNum != MAGIC_NUMBER) {
            myViewer.showError("Error reading compressed file: Magic number not found!");
            newStream.close();
            throw new IOException();
        }

        int headerFormat = inStream.readBits(BITS_PER_INT);
        if (headerFormat == STORE_COUNTS) {
            treeRoot = constructSCFTree(inStream);
        } else if (headerFormat == STORE_TREE) {
            inStream.readBits(BITS_PER_INT);
            treeRoot = constructSTFTree(inStream);
        } else {
            myViewer.showError("Incorrect header format");
            newStream.close();
            throw new IOException();
        }

        int bitsWritten = (treeRoot == null) ? 0:
                    writeBitsForUncompression(inStream, newStream, treeRoot);
        myViewer.update("Successful Uncompressing: " + bitsWritten + " bits written.");
        return bitsWritten;
    }

    /**
     * During un-compression, the methods takes in a BitInputStream of data
     * in STF format. Then, based on the values in the stream, it reconstructs
     * a Huffman tree to later covert the codes to values.
     * @param inStream The BitInputStream of data
     * @return TreeNode, symbolizing the Huffman tree generated
     * @throws IOException
     */
    private TreeNode constructSTFTree(BitInputStream inStream) throws IOException {
        int bit = inStream.readBits(1);
        TreeNode newNode;
        if (bit == BIT_FOR_INTERNAL_NODE) { //internal
            newNode = new TreeNode(-1, 0); //freq doesnt matter
            newNode.setLeft(constructSTFTree(inStream));
            newNode.setRight(constructSTFTree(inStream));
            return newNode;
        } else if (bit == BIT_FOR_LEAF) {
            newNode = new TreeNode(inStream.readBits(BITS_PER_WORD + 1), 0);
            return newNode;
        } else {
            throw new IOException("Error when reading in the huffman tree for STF!");
        }
    }

    /**
     * During un-compression, the methods takes in a BitInputStream of data
     * in SCF format. Then, while reading the file, every 8 bits symbolizes
     * the frequency of that particular value (when iterating through Alph_size).
     * @param inStream The BitInputStream of data
     * @return TreeNode, symbolizing the Huffman tree generated
     * @throws IOException
     */
    private TreeNode constructSCFTree(BitInputStream inStream) throws IOException {
        HuffmanTree huffTree = new HuffmanTree();
        for (int i = 0; i < ALPH_SIZE; i++) {
            int freq = inStream.readBits(BITS_PER_INT);
            if(freq > 0) {
                huffTree.frequencies.put(i, freq);
            }
        }

        //Redo the process of generating a Huffman tree through the frequencies
        huffTree.frequencies.put(PSEUDO_EOF, 1);
        huffTree.buildQueue();
        huffTree.buildTree();
        return huffTree.tree;
    }

    /**
     * Based on the tree given, read every chunk code given.
     * 0 symbolizes moving to the left along the tree and 1 symbolizes the right direction.
     * When a leaf is hit, this is where the value is stored for the previously given chunk code
     * Write this value into the newStream
     * @param inStream The data to read
     * @param newStream The data to write bits for values of leaf
     * @param treeRoot The Huffman Tree used to uncompress
     * @return An integer denoting the number of bits we wrote on to the new file
     * @throws IOException
     */
    private int writeBitsForUncompression(BitInputStream inStream, BitOutputStream
                                        newStream, TreeNode treeRoot) throws IOException {
        int bitsWritten = 0;
        TreeNode temp = treeRoot;
        boolean reachedEOF = false;

        while (!reachedEOF) {
            //Move along a particular direction
            int dir = inStream.readBits(1);
            temp = (dir == BIT_FOR_LEFTDIR) ? temp.getLeft() : temp.getRight();

            if (temp.isLeaf()) { //If we hit a leaf
                int value = temp.getValue();
                if (value == PSEUDO_EOF) {
                    reachedEOF = true; //We are done reading the file,
                    //terminate the loop
                } else { //This is the data, now write this
                    newStream.writeBits(BITS_PER_WORD, value);
                    bitsWritten += BITS_PER_WORD;
                    temp = treeRoot; //Restore back to start to decipher other codes
                }
            }

        }
        newStream.close();
        return bitsWritten;
    }


    /**
     * Sets the myViewer instance to a specified viewer
     * @param viewer is the view for communicating.
     */
    public void setViewer(IHuffViewer viewer) {
        myViewer = viewer;
    }

    /**
     * Shows the string in the myViewer
     * @param s the input string
     */
    private void showString(String s){
        if (myViewer != null) {
            myViewer.update(s);
        }
    }


    /**
     * A nested static class that develops a Huffman Tree and encodes
     * codes for values fed.
     */
    private static class HuffmanTree {

        private final TreeMap<Integer, Integer> frequencies; //frequencies of bit sequences

        private final PriorityQueue314<TreeNode> queue; //A PQ that is used to develop HuffmanTree
        private TreeNode tree;

        //map the original 8-bit chunk to the code from the path in huffman tree
        private final HashMap<Integer, String> chunkCodes;

        private int internalNodes;
        private int leaves;

        /**
         * Constructor for the Huffman Tree
         */
        public HuffmanTree() {
            frequencies = new TreeMap<>();
            queue = new PriorityQueue314<>();
            tree = null;
            chunkCodes = new HashMap<>();

            internalNodes = 0;
            leaves = 0;
        }

        /**
         * Generates the Huffman encoded chunk values based on the input stream
         * @param stream The BitInputStream, the data to compress and generate
         *               chunk values for
         * pre: stream != null
         * post: Generates the chunk codes and creates the Huffman Encode tree
         * @throws IOException
         */
        public void generateHuffmanTree(BitInputStream stream) throws IOException {
            if (stream == null) {
                throw new IllegalArgumentException("You cannot pass in a null " +
                        "stream of data to read");
            }

            countFrequencies(stream);
            buildQueue();
            buildTree();
            mapChunkToCodes();
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
        private void countFrequencies(BitInputStream stream) throws IOException {
            int nextSequence = stream.read();
            while (nextSequence != -1) {
                if (frequencies.get(nextSequence) == null) { //first time
                    frequencies.put(nextSequence, 1);
                } else {
                    frequencies.put(nextSequence, frequencies.get(nextSequence) + 1);
                }
                nextSequence = stream.read();
            }

            frequencies.put(PSEUDO_EOF, 1); //The Pseudo-EOF value
        }


        /**
         * Generate a PriorityQueue that first creates a TreeNode for
         * every sequence-frequency pairing. Then, it queues them and prioritizes
         * the queue based on the frequency.
         */
        private void buildQueue() {
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
        private void buildTree() {
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
        private void mapChunkToCodes() {
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
                buildCode(node.getLeft(), codeSoFar + (BIT_FOR_LEFTDIR + ""));
                buildCode(node.getRight(), codeSoFar + (BIT_FOR_RIGHTDIR + ""));
                internalNodes++;
            }
        }


    }

}
