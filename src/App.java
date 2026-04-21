import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class App {
    public static void main(String[] args) throws Exception {
        App p = new App();
        p.principal();
    }

    Object obj = null;
    JSONArray series = null;
    JSONObject langs = null;

    public void principal() {
        int opt=0;
        do {
            System.out.println("(1) Cargar JSON");
            System.out.println("(2) Contar series");
            System.out.println("(3) Ver nombre y nombre original");
            System.out.println("(4) Ver idioma original");
            System.out.println("(5) Ver país origen");
            System.out.println("(6) Ver géneros");
            System.out.println("(7) Ver breaking bad");
            System.out.println("(8) Ver serie con mejor puntuación");
            System.out.println("(9) Ver serie con peor puntuación");
            System.out.println("(10) Ver serie con más episodios");
            System.out.println("(11) Cambiar idioma");
            System.out.println("(12) Guardar en tvs_modificat.json");
            opt = readInt();
            switch (opt) {
                case 1:
                    loadJSON();
                    break;
                case 2:
                    countShows();
                    break;
                case 3:
                    showNames();
                    break;
                case 4:
                    showLang();
                    break;
                case 5:
                    showCountry();
                    break;
                case 6:
                    showGenres();
                    break;
                case 7:
                    showBreaking();
                    break;
                case 8:
                    showBestRated();
                    break;
                case 9:
                    showWorstRated();
                    break;
                case 10:
                    showMostEpisodes();
                    break;
                case 11:
                    changeLanguage();
                    break;
                case 12:
                    saveJSON();
                    break;
                default:
                    break;
            }
        } while (opt!=0);
        
    }

    public void saveJSON() {
        try (FileWriter file = new FileWriter("json-pe10/tvs_modificat.json")) {
            file.write(series.toJSONString());
            file.flush();
            System.out.println("(+) JSON guardado correctamente");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeLanguage() {
        if (langs==null) {
            loadLangs();
            System.out.println("(+) Idiomas cargados correctamente");
        }

        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            String langCode = (String)show.get("original_language");
            if (langs.containsKey(langCode)) {
                String langName = (String)langs.get(langCode);
                show.put("original_language", langName);
            }
        }
        System.out.println("(+) Idiomas cambiados correctamente");
    }

    public void showMostEpisodes() {
        int maxEpisodes = 0;
        JSONObject mostEpisodesShow = null;
        for (Object o:series) {
            JSONObject show = (JSONObject) o;

            Object episodesObj = show.get("number_of_episodes");
            if (episodesObj != null) {
                int episodes = ((Number)show.get("number_of_episodes")).intValue();
                if (episodes > maxEpisodes) {
                    maxEpisodes = episodes;
                    mostEpisodesShow = show;
                }
            }
        }
        if (mostEpisodesShow != null) {
            String name = (String)mostEpisodesShow.get("name");
            System.out.println("Serie con más episodios: "+name);
            System.out.println("Número de episodios: "+maxEpisodes);
        } else {
            System.out.println("No se encontraron series.");
        }
    } 

    public void showWorstRated() {
        double worstRating = 10.0;
        JSONObject worstShow = null;
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            double rating = ((Number)show.get("vote_average")).doubleValue();
            if (rating < worstRating) {
                worstRating = rating;
                worstShow = show;
            }
        }
        if (worstShow != null) {
            String name = (String)worstShow.get("name");
            System.out.println("Serie con peor puntuación: "+name);
            System.out.println("Puntuación: "+worstRating);
        } else {
            System.out.println("No se encontraron series.");
        }
    }

    public void showBestRated() {
        double bestRating = 0.0;
        JSONObject bestShow = null;
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            double rating = ((Number)show.get("vote_average")).doubleValue();
            if (rating > bestRating) {
                bestRating = rating;
                bestShow = show;
            }
        }
        if (bestShow != null) {
            String name = (String)bestShow.get("name");
            System.out.println("Serie con mejor puntuación: "+name);
            System.out.println("Puntuación: "+bestRating);
        } else {
            System.out.println("No se encontraron series.");
        }
    }

    public void showBreaking() {
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            String name = (String)show.get("name");
            if (name.equalsIgnoreCase("breaking bad")) {
                System.out.println("Nombre: "+name);
                String lang = (String)show.get("original_language");
                System.out.println("Idioma Original: "+lang);
                JSONArray countries = (JSONArray)show.get("origin_country");
                System.out.println("País de Origen:");
                for (Object c:countries) {
                    String country = (String) c;
                    System.out.println("- "+country);
                }
                JSONArray genres = (JSONArray)show.get("genres");
                System.out.println("Géneros:");
                for (Object g:genres) {
                    JSONObject genre = (JSONObject) g;
                    System.out.println("- "+(String)genre.get("name"));
                }
                double points = (Double)show.get("vote_average");
                System.out.println("Puntuación: "+points);
                String description = (String)show.get("overview");
                System.out.println("Descripción: "+description);
            }
        }
    }

    public void showGenres() {
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            String name = (String)show.get("name");
            System.out.println("Nombre: "+name);
            JSONArray genres = (JSONArray)show.get("genres");
            System.out.println("Géneros:");
            for (Object g:genres) {
                JSONObject genre = (JSONObject) g;
                System.out.println("- "+(String)genre.get("name"));
            }
        }
    }

    public void showCountry() {
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            String name = (String)show.get("name");
            System.out.println("Nombre: "+name);
            JSONArray countries = (JSONArray)show.get("origin_country");
            System.out.println("País de Origen:");  
            for (Object c:countries) {
                String country = (String) c;
                System.out.println("- "+country);
            }
        }
    }

    public void showLang() {
        for (Object o:series) {
            JSONObject show = (JSONObject) o;
            String name = (String)show.get("name");
            System.out.println("Nombre: "+name);
            String lang = (String)show.get("original_language");
            System.out.println("Idioma Original: "+lang);
        }
    }

    public void showNames() {
        for (Object o : series) {
            JSONObject show = (JSONObject) o;
            String name = (String)show.get("name");
            String ogName = (String)show.get("original_name");
            System.out.println("Nombre: "+name);
            System.out.println("Nombre Original: "+ogName);
        }
    }

    public void countShows() {
        if (series != null) {
            System.out.println("Hay un total de " + series.size() + " series");
        } else {
            System.out.println("Primero tienes que cargar el JSON");
        }
    }

    public void loadLangs() {
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader("json-pe10/languages.json"));
            langs = (JSONObject) obj;
            System.out.println("(+) JSON Cargado correctamente");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void loadJSON() {
        JSONParser parser = new JSONParser();
        try {
            obj = parser.parse(new FileReader("json-pe10/tvs.json"));
            series = (JSONArray) obj;
            System.out.println("(+) JSON Cargado correctamente");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public int readInt() {
        Scanner s = new Scanner(System.in);
        Boolean validNum=false;
        int num=0;
        while (!validNum) {
            try {
                num = s.nextInt();
                validNum=true;
            } catch (InputMismatchException e) {
                System.out.println("(!) Número inválido.");
                s.nextLine();
            }
        }
        return num;
    }

}
