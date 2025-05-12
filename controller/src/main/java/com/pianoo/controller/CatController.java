package com.pianoo.controller;

import com.pianoo.view.ICatFrame;
import com.pianoo.view.ICatListener;

public class CatController implements ICatController, ICatListener {

    private final ICatFrame catFrame;
    private final IController controller;

    public CatController(ICatFrame catFrame, IController controller) {
        this.catFrame = catFrame;
        this.controller = controller;

        // Définir le listener
        catFrame.setCatPlayListener(this);
    }

    @Override
    public void onPlayCat() {
        // Appeler la méthode de l'interface IController
        //controller.onPlayCat();
    }
}