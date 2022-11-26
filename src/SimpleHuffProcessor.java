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

public class SimpleHuffProcessor implements IHuffProcessor {

    private IHuffViewer myViewer;
    private FrequencyCounter counter = new FrequencyCounter();


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
        counter = new FrequencyCounter();
        counter.countFrequencies(new BitInputStream(in));
        counter.buildQueue();
        counter.buildTree();
        counter.mapChunkToCodes();
        myViewer.update(counter.frequencies.toString());
        //original size: count sum of (frequencies * 8)
        //new size: count sum of (frequencies * string length of new shiz)
        myViewer.update(counter.chunkCodes.toString());
        int oldSize = 0;
        for(Integer key : counter.frequencies.keySet()) {
            oldSize += (8 * counter.frequencies.get(key));
        }
        int newSize = 0;
        for(Integer key : counter.chunkCodes.keySet()) {
            newSize += (counter.frequencies.get(key) * counter.chunkCodes.get(key).length());
        }
        myViewer.update("Old Size: " + oldSize);
        myViewer.update("New Size: " + newSize);

//        showString("Not working yet");
//        myViewer.update("Still not working");
//        throw new IOException("preprocess not implemented");
        return 0;
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
        throw new IOException("compress is not implemented");
        //return 0;
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
}
