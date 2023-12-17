/*************************************************************************************************************************
 *
 * Tamaan Mo Ko Beybe
 * Genre: 2-Player Arcade
 * 
 * 
 * Objectives:
 * This is a two-player game consisting of a throwing team and a dodger. 
 * The goal of the throwing team is to hit the dodger with the ball before the time runs out.
 * The goal of the dodger is to dodge the ball until the time runs out.
 * 
 * 
 * Overview:
 * In the Philippines, Tamaang Tao (Dodgeball) is widely played by Filipinos of all ages. It is basically a group game where the tagging team(taya) aims to eliminate opponents by throwing a ball at dodgers. Target players (pain) are in the middle of the rectangular playing area while tagging teams are located at the end lines. To get the point, the throwers must strike the dodger within a set amount of time, while the dodgers must dodge the ball until the time runs out.
 * 
 * 
 * Controls:
 * Throwing team
 * Mouse position, mouse left and right button, left and right arrow keys
 * 
 * Dodger
 * W, A, S, D keys
 * 
 * @author Jomar Monreal, Alessandro Marcus Ocampo, James Carl Villarosa
 * @date 2023-12-18 
 *************************************************************************************************************************/

package main;

import javafx.application.Application;
import javafx.stage.Stage;
import gameplay.Gameplay;

public class App extends Application 
{
		
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) 
    {
       Gameplay gamePlay = new Gameplay();
       gamePlay.setStage(stage);
    }
    
    
}
