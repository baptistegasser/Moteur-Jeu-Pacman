package fr.univ.pacman.map;

public class LoadMap {

    /**
     * This function permit to load map and generate object
     *
     * @param map The map for place object
     * @param mapMatrice The game design model
     * @param tileSize The size of each object
     */
    public static void loadMap(Map map, int[][] mapMatrice, int tileSize) {
        if (mapMatrice.length <= 0) {
            System.out.println("The model are empty");
            return;
        }

        // Calcul the max size who an object can be place
        int mapWight = ((mapMatrice[0].length-1)*tileSize)/2;
        int mapHeight = ((mapMatrice.length-1)*tileSize)/2;

        for (int i = 0; i < mapMatrice.length;i++) {
            for (int j = 0; j<mapMatrice[i].length; j++) {
                if (mapMatrice[i][j] == 1) {
                    // Calcul the position of the object
                    int x = (j*tileSize)-mapWight;
                    int y = (i*tileSize)-mapHeight;

                    map.add(new Wall(x, y));
                }
            }
        }
    }
}
