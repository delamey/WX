package com.example.dell.wx;

import dagger.Component;

@Component(modules = TextViewModule.class)
public interface MainComponent {

    void inject(MainActivity activity);
}
