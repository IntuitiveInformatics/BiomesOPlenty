/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.biome.overworld;

import biomesoplenty.api.config.IBOPWorldSettings;
import biomesoplenty.api.config.IBOPWorldSettings.GeneratorType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
 
public class BiomeGenOriginBeach extends BOPOverworldBiome
{
    public BiomeGenOriginBeach()
    {
        super("origin_beach", new PropsBuilder("Origin Beach").withGuiColour(10341485).withTemperature(0.6F).withRainfall(0.6F));

        // terrain
        this.terrainSettings.avgHeight(64).heightVariation(1, 1);
    
        this.skyColor = 8441086;
        
        this.canSpawnInBiome = false;
        this.canGenerateVillages = false;
        this.hasBiomeEssence = false;
        this.canGenerateRivers = false;
        
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySpider.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 100, 4, 4));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 100, 4, 4));
        
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        
        clearWeights();
        
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SAND.getDefaultState();
    }
    
    @Override
    public void applySettings(IBOPWorldSettings settings)
    {
    	this.removeGenerator("roots");
        this.removeGenerator("glowshrooms");
        this.removeGenerator("miners_delight");

        if (!settings.isEnabled(GeneratorType.GEMS)) {this.removeGenerator("ruby"); this.removeGenerator("topaz");
        this.removeGenerator("amber"); this.removeGenerator("peridot"); this.removeGenerator("malachite");
        this.removeGenerator("sapphire"); this.removeGenerator("tanzanite"); this.removeGenerator("amethyst");}
         
        if (!settings.isEnabled(GeneratorType.POISON_IVY)) {this.removeGenerator("poison_ivy");}
        
        if (!settings.isEnabled(GeneratorType.BERRY_BUSHES)) {this.removeGenerator("berry_bushes");}
        
        if (!settings.isEnabled(GeneratorType.NETHER_HIVES)) {this.removeGenerator("hive");}
        
        if (!settings.isEnabled(GeneratorType.THORNS)) {this.removeGenerator("thorns");}
        
        if (!settings.isEnabled(GeneratorType.QUICKSAND)) {this.removeGenerator("quicksand");}
        
        if (!settings.isEnabled(GeneratorType.HOT_SPRINGS)) {this.removeGenerator("hot_springs");}
        
        if (!settings.isEnabled(GeneratorType.LIQUID_POISON)) {this.removeGenerator("poison_lakes");}
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return getModdedBiomeGrassColor(12757624);
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return getModdedBiomeFoliageColor(12749925);
    }
}
