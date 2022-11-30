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
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.TreeMap;

/** EMPIRICAL DATA:
 *
 * calgary -
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\bib.hf
 * Reached
 * bib from	 111261 to	 73795 in	 0.420
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\book1.hf
 * Reached
 * book1 from	 768771 to	 439409 in	 1.975
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\book2.hf
 * Reached
 * book2 from	 610856 to	 369335 in	 1.295
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\geo.hf
 * Reached
 * geo from	 102400 to	 73592 in	 0.221
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\news.hf
 * Reached
 * news from	 377109 to	 247428 in	 0.723
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\obj1.hf
 * Reached
 * obj1 from	 21504 to	 17085 in	 0.051
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\obj2.hf
 * Reached
 * obj2 from	 246814 to	 195131 in	 0.569
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper1.hf
 * Reached
 * paper1 from	 53161 to	 34371 in	 0.101
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper2.hf
 * Reached
 * paper2 from	 82199 to	 48649 in	 0.147
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper3.hf
 * Reached
 * paper3 from	 46526 to	 28309 in	 0.086
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper4.hf
 * Reached
 * paper4 from	 13286 to	 8894 in	 0.031
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper5.hf
 * Reached
 * paper5 from	 11954 to	 8465 in	 0.029
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\paper6.hf
 * Reached
 * paper6 from	 38105 to	 25057 in	 0.074
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\pic.hf
 * Reached
 * pic from	 513216 to	 107586 in	 0.335
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\progc.hf
 * Reached
 * progc from	 39611 to	 26948 in	 0.080
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\progl.hf
 * Reached
 * progl from	 71646 to	 44017 in	 0.137
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\progp.hf
 * Reached
 * progp from	 49379 to	 31248 in	 0.101
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\calgary\calgary\trans.hf
 * Reached
 * trans from	 93695 to	 66252 in	 0.195
 * --------
 * total bytes read: 3251493
 * total compressed bytes 1845571
 * total percent compression 43.239
 * compression time: 6.570
 *
 * BooksAndHTML -
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\A7_Recursion.html.hf
 * Reached
 * A7_Recursion.html from	 41163 to	 26189 in	 0.172
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\CiaFactBook2000.txt.hf
 * Reached
 * CiaFactBook2000.txt from	 3497369 to	 2260664 in	 6.766
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\jnglb10.txt.hf
 * Reached
 * jnglb10.txt from	 292059 to	 168618 in	 0.524
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\kjv10.txt.hf
 * Reached
 * kjv10.txt from	 4345020 to	 2489768 in	 7.412
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\melville.txt.hf
 * Reached
 * melville.txt from	 82140 to	 47364 in	 0.177
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\quotes.htm.hf
 * Reached
 * quotes.htm from	 61563 to	 38423 in	 0.132
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\rawMovieGross.txt.hf
 * Reached
 * rawMovieGross.txt from	 117272 to	 53833 in	 0.166
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\revDictionary.txt.hf
 * Reached
 * revDictionary.txt from	 1130523 to	 611618 in	 2.013
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\syllabus.htm.hf
 * Reached
 * syllabus.htm from	 33273 to	 21342 in	 0.072
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\BooksAndHTML\BooksAndHTML\ThroughTheLookingGlass.txt.hf
 * Reached
 * ThroughTheLookingGlass.txt from	 188199 to	 110293 in	 0.413
 * --------
 * total bytes read: 9788581
 * total compressed bytes 5828112
 * total percent compression 40.460
 * compression time: 17.847
 *
 * waterloo -
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\clegg.tif.hf
 * Reached
 * clegg.tif from	 2149096 to	 2034595 in	 6.252
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\frymire.tif.hf
 * Reached
 * frymire.tif from	 3706306 to	 2188593 in	 6.191
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\lena.tif.hf
 * Reached
 * lena.tif from	 786568 to	 766146 in	 2.161
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\monarch.tif.hf
 * Reached
 * monarch.tif from	 1179784 to	 1109973 in	 3.101
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\peppers.tif.hf
 * Reached
 * peppers.tif from	 786568 to	 756968 in	 2.142
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\sail.tif.hf
 * Reached
 * sail.tif from	 1179784 to	 1085501 in	 3.069
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\serrano.tif.hf
 * Reached
 * serrano.tif from	 1498414 to	 1127645 in	 4.020
 * compressing to: C:\Users\zayaa\IdeaProjects\Assignment10\waterloo\waterloo\tulips.tif.hf
 * Reached
 * tulips.tif from	 1179784 to	 1135861 in	 3.481
 * --------
 * total bytes read: 12466304
 * total compressed bytes 10205282
 * total percent compression 18.137
 * compression time: 30.417
 *
 *
 *
 *
 *
 */

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private HuffmanTree counter;

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
        newSize = 0;
        oldSize = 0;

        counter = new HuffmanTree();
        counter.countFrequencies(new BitInputStream(in));
        counter.buildQueue();
        counter.buildTree();
        counter.mapChunkToCodes();
     //   myViewer.update(counter.frequencies.toString());

        header = headerFormat;

        //original size: count sum of (frequencies * 8)
        //new size: count sum of (frequencies * string length of new shiz)
   //     myViewer.update(counter.chunkCodes.toString());

        for (Integer key : counter.frequencies.keySet()) {
            oldSize += (BITS_PER_WORD * counter.frequencies.get(key));
        }

        oldSize -= 8; //PEOF was never included in the uncompressed file

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

   //     myViewer.update("Old Size: " + oldSize);
   //     myViewer.update("New Size: " + newSize);

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

        if(force || (newSize < oldSize)) {

            System.out.println("Reached");
            BitInputStream inBitStream = new BitInputStream(in);
            BitOutputStream outBitStream = new BitOutputStream(out);

            //Step 1: Write the magic number
            outBitStream.writeBits(BITS_PER_INT, MAGIC_NUMBER);

            //Step 2: Write the header format
            outBitStream.writeBits(BITS_PER_INT, header);

            //Step 3: Write the STF header data
            if (header == STORE_TREE) {
                System.out.println("TREE");
                outBitStream.writeBits(BITS_PER_INT, counter.internalNodes + (counter.leaves * 10));
                writeHeaderDataSTF(outBitStream, counter.tree);
            } else if (header == STORE_COUNTS) {
//                outBitStream.writeBits(BITS_PER_INT, ALPH_SIZE * BITS_PER_INT);
                writeHeaderDataSCF(outBitStream);
            }

            // Step 4: Using the chunk codes, write out the data
            int read = inBitStream.read();
            while (read != -1) {
                //write individual bits in the string
                convertSequence(counter.chunkCodes.get(read), outBitStream);
                read = inBitStream.read(); //move to next chunk
            }

            //Step 5: Add the PEOF value
            convertSequence(counter.chunkCodes.get(ALPH_SIZE), outBitStream);

            outBitStream.close();

        //    myViewer.showMessage("Bits of the new file: " + newSize);

            return newSize;
        }
        return 0;
    }

    private void writeHeaderDataSTF(BitOutputStream outBitStream, TreeNode node) throws IOException {
        if (node.isLeaf()) {
            outBitStream.writeBits(1, 1);
            outBitStream.writeBits(BITS_PER_WORD + 1, node.getValue());
        } else {
            outBitStream.writeBits(1, 0);
            writeHeaderDataSTF(outBitStream, node.getLeft());
            writeHeaderDataSTF(outBitStream, node.getRight());
        }
    }

    private void writeHeaderDataSCF(BitOutputStream outBitStream) throws IOException {
        for (int i = 0; i < IHuffConstants.ALPH_SIZE; i++){
            int valToAdd;
            if(counter.frequencies.get(i) == null){
                valToAdd = 0;
            } else {
                valToAdd = counter.frequencies.get(i);
            }
            outBitStream.writeBits(BITS_PER_INT,valToAdd);
        }
    }

    private void convertSequence(String code, BitOutputStream outBitStream) throws IOException {
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
        //assuming STF format, get size of tree
        BitOutputStream newStream = new BitOutputStream(out);
        BitInputStream inStream = new BitInputStream(in);
        int magicNum = inStream.readBits(BITS_PER_INT); //magic num at beginning
        if (magicNum != MAGIC_NUMBER) {
            throw new IOException("Magic number not found!");
        } else {
            int bitsWritten = 0;
            int headerFormat = inStream.readBits(BITS_PER_INT);
            TreeNode treeRoot = null;
            if (headerFormat == STORE_COUNTS) {
                HuffmanTree huffTree = new HuffmanTree();
                for (int i = 0; i < ALPH_SIZE; i++) {
                    int freq = inStream.readBits(BITS_PER_INT);
                    if(freq > 0) {
                        huffTree.frequencies.put(i, freq);
                    }
                }
                huffTree.frequencies.put(PSEUDO_EOF, 1);
                huffTree.buildQueue();
                huffTree.buildTree();
                treeRoot = huffTree.tree;
          //      myViewer.update(huffTree.frequencies.toString());
            } else if (headerFormat == STORE_TREE) {
                inStream.readBits(BITS_PER_INT);
                treeRoot = reconstructTree(inStream);

            } else { //put another else if, if STORE_CUSTOM is specified
                throw new IOException("No valid header found!");
            }
            //now, we have our tree after deciphering the format
            TreeNode temp = treeRoot;
            boolean reachedEOF = false;
            int ogBits = 0;
            while (!reachedEOF) {
                int dir = inStream.readBits(1);
                if (dir == 0) {
                    ogBits++;
                    temp = temp.getLeft();
                } else {
                    ogBits++;
                    temp = temp.getRight();
                }
                if (temp.isLeaf()) {
                    int value = temp.getValue();
                    if (value == PSEUDO_EOF) {
                        reachedEOF = true;
                    } else {
                        newStream.writeBits(BITS_PER_WORD, value);
                        bitsWritten += BITS_PER_WORD;
                        temp = treeRoot;
                    }
                }
            }
            newStream.close();
            return bitsWritten;
        }
    }

    private TreeNode reconstructTree(BitInputStream inStream) throws IOException {
        int bit = inStream.readBits(1);
        TreeNode newNode;
        if (bit == 0) { //internal
            newNode = new TreeNode(-1, 0); //freq doesnt matter
            newNode.setLeft(reconstructTree(inStream));
            newNode.setRight(reconstructTree(inStream));
            return newNode;
        } else if (bit == 1) {
            newNode = new TreeNode(inStream.readBits(BITS_PER_WORD + 1), 0);
            return newNode;
        } else {
            throw new IOException("Error when reading in the huffman tree for STF!");
        }
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

        //TODO: group together methods, because implementation details do not matter

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

            frequencies.put(PSEUDO_EOF, 1); //The Pseudo-EOF value
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
