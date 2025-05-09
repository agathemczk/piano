package com.pianoo.model;

import java.util.Scanner;

public class Piano implements IPiano {
    private int nbOctaves;
    
    public int chooseNbOctaves() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the number of octaves you want to play with (1 to 7): ");
        nbOctaves = scanner.nextInt();
        while (nbOctaves < 1 || nbOctaves > 7) {
            System.out.println("Please choose a number between 1 and 7: ");
            nbOctaves = scanner.nextInt();
        }
        return nbOctaves;
    }

    public void setNbOctaves(int nbOctaves) {
        this.nbOctaves = nbOctaves;
    }

}

