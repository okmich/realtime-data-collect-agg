/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.hackerday.client.tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author m.enudi
 */
public class FileMerger {

    List<String> allContents;
    private static final Logger LOG = Logger.getLogger(FileMerger.class.getName());

    public FileMerger() {
        allContents = new ArrayList<>(100000);
    }

    public static void main(String[] args) {
        if (args.length < 4) {
            System.err.println("General argument: <data_dir> <start_folder> <end_folder> <outputDir>");
            System.exit(-1);
        }
        //args = new String[]{"F:\\data_dump\\Geolife Trajectories 1.3\\Data", "0", "1", "F:\\data_dump\\Geolife Trajectories 1.3\\Data"};
        try {
            new FileMerger().begin(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]), args[3]);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param folder
     * @param start
     * @param end
     * @param outputDir
     * @throws IOException
     */
    public void begin(String folder, int start, int end, String outputDir) throws IOException {
        if (start >= end) {
            throw new IllegalArgumentException("start and end folder can't be the same");
        }
        File dataFolder = new File(folder);

        String file;
        for (int i = start; i <= end; i++) {
            file = leftPadWithZeros(i, 3);
            LOG.log(Level.INFO, "reading file - {0}", file);
            readTrajectoryFolder(dataFolder, file);
        }

        LOG.log(Level.INFO, "sorting all content read into a new file");
        Collections.sort(allContents, new RecordComparator());

        file = leftPadWithZeros(start, 3) + "-" + leftPadWithZeros(end, 3) + ".pltdata";
        LOG.log(Level.INFO, "sorting all content read into a new file - {0}", file);
        Files.write(Paths.get(outputDir, file),
                allContents, StandardOpenOption.CREATE_NEW);
    }

    private void readTrajectoryFolder(File dataFolder, String userFolder) throws IOException {
        Stream<Path> files = Files.list(Paths.get(dataFolder.getAbsolutePath(), userFolder, "Trajectory"));
        files.forEach((Path t) -> {
            try {
                List<String> content = Files.lines(t).map((String l) -> userFolder + "," + t.getFileName().toString().substring(0, 14)
                        + "," + l).collect(Collectors.toList());
                allContents.addAll(content.subList(6, content.size()));
            } catch (IOException ex) {
                Logger.getLogger(FileMerger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private String leftPadWithZeros(int i, int len) {
        String s = String.valueOf(i);
        if (s.length() < len) {
            for (int a = 0; a <= len - s.length(); a++) {
                s = '0' + s;
            }
        }
        return s;
    }

    private class RecordComparator implements Comparator<String> {

        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");

        @Override
        public int compare(String o1, String o2) {
            try {
                //000,39.976437,116.34093,0,306,39746.1958217593,2008-10-25,04:41:59
                String[] part1s = o1.split(",");
                Date date1 = df.parse(part1s[7] + "," + part1s[8]);

                String[] part2s = o2.split(",");
                Date date2 = df.parse(part2s[7] + "," + part2s[8]);

                return date1.compareTo(date2);
            } catch (ParseException ex) {
                Logger.getLogger(FileMerger.class.getName()).log(Level.SEVERE, null, ex);
            }
            return 0;
        }

    }
}
