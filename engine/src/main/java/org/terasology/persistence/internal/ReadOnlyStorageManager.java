/*
 * Copyright 2014 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.persistence.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;

import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.entity.internal.EngineEntityManager;
import org.terasology.module.ModuleEnvironment;
import org.terasology.network.Client;
import org.terasology.network.ClientComponent;
import org.terasology.world.chunks.Chunk;

/**
 * A {@link org.terasology.persistence.StorageManager} that performs reading only.
 * @author Martin Steiger
 */
public final class ReadOnlyStorageManager extends AbstractStorageManager {

    public ReadOnlyStorageManager(Path savePath, ModuleEnvironment environment, EngineEntityManager entityManager) {
        this(savePath, environment, entityManager, true);
    }

    public ReadOnlyStorageManager(Path savePath, ModuleEnvironment environment, EngineEntityManager entityManager, boolean storeChunksInZips) {
        super(savePath, environment, entityManager, storeChunksInZips);
    }

    @Override
    public void finishSavingAndShutdown() {
        // don't care
    }

    @Override
    public void requestSaving() {
        // don't care
    }

    @Override
    public void waitForCompletionOfPreviousSaveAndStartSaving() {
        // don't care
    }

    @Override
    public void deactivateChunk(Chunk chunk) {
        Collection<EntityRef> entitiesOfChunk = getEntitiesOfChunk(chunk);

        for (EntityRef entity : entitiesOfChunk) {
            deactivateOrDestroyEntityRecursive(entity);
        }
    }

    @Override
    public void update() {
    }

    @Override
    public boolean isSaving() {
        return false;
    }

    @Override
    public void checkAndRepairSaveIfNecessary() throws IOException {
        // can't do that ..
    }

    @Override
    public void deleteWorld() {
        // can't do that ..
    }

    @Override
    public void deactivatePlayer(Client client) {
        EntityRef character = client.getEntity().getComponent(ClientComponent.class).character;
        deactivateOrDestroyEntityRecursive(character);
    }
}
