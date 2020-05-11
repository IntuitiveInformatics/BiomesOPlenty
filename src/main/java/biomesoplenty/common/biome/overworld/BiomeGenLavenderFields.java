/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.biome.overworld;

import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.enums.BOPClimates;
import biomesoplenty.api.enums.BOPFlowers;
import biomesoplenty.api.enums.BOPGems;
import biomesoplenty.api.enums.BOPPlants;
import biomesoplenty.api.enums.BOPTrees;
import biomesoplenty.api.enums.BOPWoods;
import biomesoplenty.api.generation.GeneratorStage;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPGrass;
import biomesoplenty.common.block.BlockBOPLeaves;
import biomesoplenty.common.world.generator.GeneratorFlora;
import biomesoplenty.common.world.generator.GeneratorGrass;
import biomesoplenty.common.world.generator.GeneratorOreSingle;
import biomesoplenty.common.world.generator.GeneratorWeighted;
import biomesoplenty.common.world.generator.tree.GeneratorBasicTree;
import biomesoplenty.common.world.generator.tree.GeneratorBigTree;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.util.math.BlockPos;
 
public class BiomeGenLavenderFields extends BOPOverworldBiome
{
    public BiomeGenLavenderFields()
    {
        super("lavender_fields", new PropsBuilder("Lavender Fields").withGuiColour(11035852).withTemperature(0.7F).withRainfall(0.7F));

        // terrain
        this.terrainSettings.avgHeight(64).heightVariation(4, 12);
        
        this.topBlock = BOPBlocks.grass.getDefaultState().withProperty(BlockBOPGrass.VARIANT, BlockBOPGrass.BOPGrassType.SILTY);
        this.fillerBlock = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BlockBOPDirt.BOPDirtType.SILTY);
    
        this.canSpawnInBiome = false;
        this.canGenerateVillages = false;
        
        this.addWeight(BOPClimates.MEDITERANEAN, 3);
        
        // flowers
        this.addGenerator("lavender", GeneratorStage.FLOWERS, (new GeneratorFlora.Builder()).amountPerChunk(50).with(BOPFlowers.LAVENDER).create());

        // trees
        GeneratorWeighted treeGenerator = new GeneratorWeighted(1);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("jacaranda", 3, (new GeneratorBasicTree.Builder()).minHeight(4).maxHeight(7).log(BOPWoods.JACARANDA).leaves(BOPTrees.JACARANDA).create());
        treeGenerator.add("oak", 1, (new GeneratorBigTree.Builder()).altLeaves(BlockBOPLeaves.paging.getVariantState(BOPTrees.FLOWERING).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false))).create());
        
        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(15);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("shortgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.SHORTGRASS).create());
        grassGenerator.add("mediumgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).create());
        //grassGenerator.add("wheatgrass", 1, (new GeneratorGrass.Builder()).with(BOPPlants.WHEATGRASS).create());
        grassGenerator.add("tallgrass", 3, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).create());
         
        // gem
        this.addGenerator("peridot", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.PERIDOT).create()); 
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return getModdedBiomeGrassColor(11966274);
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return getModdedBiomeFoliageColor(11962531);
    }
}
