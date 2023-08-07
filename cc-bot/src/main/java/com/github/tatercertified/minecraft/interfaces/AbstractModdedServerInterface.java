package com.github.tatercertified.minecraft.interfaces;

import java.util.List;

public interface AbstractModdedServerInterface extends AbstractVanillaServerInterface {
    /**
     * Used to retrieve a List of mods that have integration with Crummer Communicator
     * @return List of names of mods as Strings
     */
    List<String> getMods();
    void replaceMods(List<String> newMods);
}
