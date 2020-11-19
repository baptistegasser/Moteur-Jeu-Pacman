package fr.univ.pacman.map;

public class LoadMap {
    public static void loadMap(Map map, int[][] mapMatrice, int tileSize) {
        if (mapMatrice.length <= 0) {
            System.out.println("La matrice est vide");
            return;
        }

        System.out.println(mapMatrice[0].length);

        int mapWight = ((mapMatrice[0].length-1)*tileSize)/2;
        int mapHeight = ((mapMatrice.length-1)*tileSize)/2;

        System.out.println(mapWight);
        System.out.println(mapHeight);

        for (int i = 0; i < mapMatrice.length;i++) {
            for (int j = 0; j<mapMatrice[i].length; j++) {
                if (mapMatrice[i][j] == 1) {
                    int x = (j*tileSize)-mapWight;
                    int y = (i*tileSize)-mapHeight;

                    map.add(new Wall(x, y));
                }
            }
        }
    }
}
