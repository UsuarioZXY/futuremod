package com.futuremod.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/** Libro con todas las recetas del mod. Solo se obtiene desde el inventario creativo. */
public class GuideBook {
    private static final String[] PAGES = {
            "\u00a7l\u00a7nFUTURE MOD\u00a7r\n\n\u00a7oLibro de recetas\u00a7r\n\nAqu\u00ed aprender\u00e1s a fabricar todo lo del mod en la mesa de crafteo.\n\n- = casilla vac\u00eda",
            "\u00a7l\u00cdndice\u00a7r\n6 Mena de acero\n7 Lingote y pepitas\n8 Chip de acero\n9 Motor de acero\n10 N\u00facleo de acero\n11 Cristal reforzado\n12 Vara de acero\n13 Casco\n14 Peto",
            "\u00a7l\u00cdndice\u00a7r\n15 Pantalones\n16 Botas\n17 Set completo\n18 Espada\n19 Pico\n20 Hacha\n21 Pala\n22 Azada\n23 Herramientas",
            "\u00a7l\u00cdndice\u00a7r\n24 Tela primitiva\n25 Hilo primitivo\n26 Arco primitivo\n27 Flecha primitiva\n28 Escudo primitivo\n29 Sangrado\n30 Verdiano guerrero\n31 Verdiano arquero\n32 Verdiano escudero",
            "\u00a7l\u00cdndice\u00a7r\n33 Verdiano caballero\n34 Rodia\n35 Verdiano curandero",
            "\u00a7lMena de acero\u00a7r\n\nSe encuentra bajo tierra entre las capas -64 y 40, en piedra y en pizarra abismal.\n\nR\u00f3mpela con un pico de piedra o mejor: suelta acero sin cocer.",
            "\u00a7lLingote de acero\u00a7r\n\nCocina el acero sin cocer en un ALTO HORNO.\n\n\u00a7oEl horno normal no sirve.\u00a7r\n\n1 lingote = 9 pepitas\n9 pepitas = 1 lingote",
            "\u00a7lChip de acero\u00a7r\nL - L\nS T S\nR S R\n\nL=pararrayos\nS=lingote de acero\nT=antorcha redstone\nR=polvo de redstone",
            "\u00a7lMotor de acero\u00a7r\nS R S\nS C S\nS R S\n\nS=lingote de acero\nR=polvo de redstone\nC=chip de acero",
            "\u00a7lN\u00facleo de acero\u00a7r\nM S M\nS C S\nM S M\n\nM=motor de acero\nS=lingote de acero\nC=chip de acero",
            "\u00a7lCristal reforzado\u00a7r\n- S -\nS G S\n- S -\n\nS=lingote de acero\nG=vidrio normal\n\nDa 1 cristal.",
            "\u00a7lVara de acero\u00a7r\n- I S\nI S I\nS I -\n\nI=lingote de acero\nS=palo\n\nDa 8 varas.",
            "\u00a7lCasco de acero\u00a7r\nS C S\nS G S\n- S -\n\nS=lingote de acero\nC=chip de acero\nG=cristal reforzado\n\n+1 coraz\u00f3n",
            "\u00a7lPeto de acero\u00a7r\nS - S\nM K M\nS C S\n\nS=lingote de acero\nM=motor de acero\nK=n\u00facleo de acero\nC=chip de acero\n\n+2 corazones",
            "\u00a7lPantalones de acero\u00a7r\nM C M\nS - S\nS - S\n\nM=motor de acero\nC=chip de acero\nS=lingote de acero\n\n+1 coraz\u00f3n",
            "\u00a7lBotas de acero\u00a7r\nM - M\nS - S\n\nM=motor de acero\nS=lingote de acero\n\n+1 coraz\u00f3n y +10% de velocidad",
            "\u00a7lSet completo\u00a7r\n\nCasco, peto, pantalones y botas: +5 corazones en total.\n\nCon las 4 piezas las flechas no te hacen da\u00f1o (los dem\u00e1s proyectiles s\u00ed).",
            "\u00a7lEspada de acero\u00a7r\n- I I\nM K I\nR M -\n\nI=lingote de acero\nM=motor de acero\nK=n\u00facleo de acero\nR=vara de acero",
            "\u00a7lPico de acero\u00a7r\nI I K\n- C I\nR - I\n\nI=lingote de acero\nK=n\u00facleo de acero\nC=chip de acero\nR=vara de acero",
            "\u00a7lHacha de acero\u00a7r\n- K I\n- M I\n- R -\n\nK=n\u00facleo de acero\nI=lingote de acero\nM=motor de acero\nR=vara de acero",
            "\u00a7lPala de acero\u00a7r\n- K -\nI M I\n- R -\n\nK=n\u00facleo de acero\nI=lingote de acero\nM=motor de acero\nR=vara de acero",
            "\u00a7lAzada de acero\u00a7r\n- M I\n- K -\n- R -\n\nM=motor de acero\nI=lingote de acero\nK=n\u00facleo de acero\nR=vara de acero",
            "\u00a7lHerramientas\u00a7r\n\nLa espada, el pico, el hacha, la pala y la azada de acero tienen 2200 usos y minan como el diamante.\n\nSe reparan con lingotes de acero.",
            "\u00a7lTrozo de tela primitiva\u00a7r\n\nPon 9 fragmentos de tela primitiva llenando toda la mesa (3x3).\n\nLos fragmentos los sueltan los Verdianos.",
            "\u00a7lHilo primitivo\u00a7r\n- F -\nF S F\n- F -\n\nF=fragmento de tela primitiva\nS=hilo normal",
            "\u00a7lArco primitivo\u00a7r\nB A T\nA - T\nB A T\n\nB=hueso roto\nA=hueso de alien\u00edgena\nT=hilo primitivo",
            "\u00a7lFlecha primitiva\u00a7r\nB N -\nN A T\n- T C\n\nB=hueso roto\nN=pepita de acero\nA=hueso de alien\u00edgena\nT=hilo primitivo\nC=trozo de tela primitiva\n\nDa 8 flechas.",
            "\u00a7lEscudo primitivo\u00a7r\nA C A\nI C I\nT A T\n\nA=hueso de alien\u00edgena\nC=trozo de tela primitiva\nI=lingote de acero\nT=hilo primitivo\n\nBloquea como un escudo.",
            "\u00a7lSangrado\u00a7r\n\nLas flechas primitivas causan 5 segundos de sangrado: pierdes vida, no puedes regenerarte y la leche no lo cura.",
            "\u00a7lVerdiano guerrero\u00a7r\n\nLleva un garrote de piedra. Tiene 8 corazones y sale en manadas de 3 a 7, de d\u00eda y de noche.\n\nSuelta hueso de alien\u00edgena, hueso roto y tela.",
            "\u00a7lVerdiano arquero\u00a7r\n\nDispara flechas primitivas con su arco. Sale en grupos de 1 a 3.\n\nLos Verdianos nunca se atacan entre s\u00ed.",
            "\u00a7lVerdiano escudero\u00a7r\n\nLleva lanza y escudo primitivo. Ataca desde m\u00e1s lejos (2 corazones de da\u00f1o) y a veces bloquea tus golpes.\n\nSale en grupos de 1 a 3.",
            "\u00a7lVerdiano caballero\u00a7r\n\nLleva armadura y espada primitivas y escudo (a veces bloquea tus golpes). Cabalga sobre un Rodia.\n\nSale de d\u00eda y de noche.",
            "\u00a7lRodia\u00a7r\n\nBestia alien\u00edgena parecida a un dinosaurio. Es r\u00e1pida y fuerte (15 corazones).\n\nSale sola o con un Verdiano caballero encima.",
            "\u00a7lVerdiano curandero\u00a7r\n\nLleva un cetro y cura a los Verdianos y Rodias cercanos. No ataca.\n\nSale en grupos de 1 a 2."
    };

    public static ItemStack create() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag tag = book.getOrCreateTag();
        tag.putString("title", "Gu\u00eda de Future Mod");
        tag.putString("author", "Future Mod");
        ListTag pages = new ListTag();
        for (String page : PAGES) {
            pages.add(StringTag.valueOf(Component.Serializer.toJson(Component.literal(page))));
        }
        tag.put("pages", pages);
        return book;
    }
}
