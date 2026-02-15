package com.viktoria.viktorifit.exercise.seed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.viktoria.viktorifit.exercise.entity.ExerciseEntity;
import com.viktoria.viktorifit.exercise.repository.ExerciseRepository;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class ExerciseSeeder implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;

    @Override
    public void run(String... args) {
        if (exerciseRepository.count() == 0) {
            try {
                InputStream is = getClass().getResourceAsStream("/dataset_workout.csv");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                List<ExerciseEntity> exercises = new ArrayList<>();
                String line;
                reader.readLine(); // Lewati header CSV

                while ((line = reader.readLine()) != null) {
                    // Regex untuk memisahkan kolom berdasarkan koma, tapi mengabaikan koma di dalam tanda kutip
                    String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    if (data.length >= 9) {
                        ExerciseEntity e = new ExerciseEntity();
                        
                        String id = cleanText(data[0]);
                        String gifUrl = cleanText(data[2]);

                        // Cek jika gifUrl bernilai "None" atau kosong
                        if (gifUrl == null || gifUrl.equalsIgnoreCase("none") || gifUrl.isEmpty()) {
                            gifUrl = id + ".gif";
                        }

                        e.setExerciseId(id);
                        e.setName(cleanText(data[1]));
                        e.setGifUrl(gifUrl);
                        
                        // Konversi kolom teks array menjadi List<String>
                        e.setTargetMuscles(parseCsvList(data[3]));
                        e.setBodyParts(parseCsvList(data[4]));
                        e.setEquipments(parseCsvList(data[5]));
                        e.setSecondaryMuscles(parseCsvList(data[6]));
                        e.setInstructions(parseCsvList(data[7]));
                        
                        e.setEnvironment(cleanText(data[8]));
                        
                        exercises.add(e);
                    }
                }

                exerciseRepository.saveAll(exercises);
                System.out.println("Database Berhasil di-seeding: " + exercises.size() + " data dimasukkan ke tbl_exercise.");

            } catch (IOException e) {
                System.err.println("Gagal saat seeding data: " + e.getMessage());
            }
        }
    }

    private String cleanText(String text) {
        if (text == null) return "";
        // Menghapus tanda kutip dua yang membungkus teks di CSV
        return text.trim().replaceAll("^\"|\"$", "");
    }

    private List<String> parseCsvList(String text) {
        if (text == null || text.isEmpty() || text.equals("[]")) {
            return new ArrayList<>();
        }

        // 1. Bersihkan bungkus luar seperti ["..."] atau [...]
        String content = text.trim()
                .replaceAll("^\\[|\\]$", "") // Hapus kurung siku
                .replaceAll("^\"|\"$", "");   // Hapus kutip dua luar

        // 2. Split berdasarkan koma
        String[] parts = content.split(",");

        // 3. Bersihkan setiap item dari spasi dan kutip satu (')
        return Arrays.stream(parts)
                .map(item -> item.trim().replaceAll("^'|'$", ""))
                .filter(item -> !item.isEmpty())
                .collect(Collectors.toList());
    }
}