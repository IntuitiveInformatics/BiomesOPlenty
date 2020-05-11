/*******************************************************************************
 * Copyright 2015-2016, the Biomes O' Plenty Team
 * 
 * This work is licensed under a Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International Public License.
 * 
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/4.0/.
 ******************************************************************************/

package biomesoplenty.common.biome.overworld;

import java.util.Random;

import biomesoplenty.api.biome.BOPBiomes;
import biomesoplenty.api.block.BOPBlocks;
import biomesoplenty.api.block.BlockQueries;
import biomesoplenty.api.enums.BOPClimates;
import biomesoplenty.api.enums.BOPGems;
import biomesoplenty.api.enums.BOPPlants;
import biomesoplenty.api.enums.BOPTrees;
import biomesoplenty.api.enums.BOPWoods;
import biomesoplenty.api.generation.GeneratorStage;
import biomesoplenty.common.block.BlockBOPCoral;
import biomesoplenty.common.block.BlockBOPDirt;
import biomesoplenty.common.block.BlockBOPDoublePlant;
import biomesoplenty.common.block.BlockBOPGrass;
import biomesoplenty.common.block.BlockBOPLilypad;
import biomesoplenty.common.block.BlockBOPMushroom;
import biomesoplenty.common.block.BlockBOPPlant;
import biomesoplenty.common.util.biome.GeneratorUtils.ScatterYMethod;
import biomesoplenty.common.world.generator.GeneratorColumns;
import biomesoplenty.common.world.generator.GeneratorDoubleFlora;
import biomesoplenty.common.world.generator.GeneratorFlora;
import biomesoplenty.common.world.generator.GeneratorGrass;
import biomesoplenty.common.world.generator.GeneratorOreSingle;
import biomesoplenty.common.world.generator.GeneratorWaterside;
import biomesoplenty.common.world.generator.GeneratorWeighted;
import biomesoplenty.common.world.generator.tree.GeneratorBigTree;
import biomesoplenty.common.world.generator.tree.GeneratorTaigaTree;
import biomesoplenty.common.world.generator.tree.GeneratorTwigletTree;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenFossils;

public class BiomeGenFen extends BOPOverworldBiome
{
        
    public BiomeGenFen()
    {
        super("fen", new PropsBuilder("Fen").withGuiColour(0xBAC481).withTemperature(0.4F).withRainfall(0.4F));

        // terrain
        this.terrainSettings.avgHeight(63).heightVariation(8, 6).octaves(1, 4, 3, 1, 1, 0);
        
        this.topBlock = BOPBlocks.grass.getDefaultState().withProperty(BlockBOPGrass.VARIANT, BlockBOPGrass.BOPGrassType.LOAMY);
        this.fillerBlock = BOPBlocks.dirt.getDefaultState().withProperty(BlockBOPDirt.VARIANT, BlockBOPDirt.BOPDirtType.LOAMY);
        
        this.canSpawnInBiome = false;
        this.canGenerateRivers = false;
        this.canGenerateVillages = false;
        
        if (BOPBiomes.gravel_beach.isPresent())
        {
        	this.beachBiomeLocation = ((BOPOverworldBiome)BOPBiomes.gravel_beach.get()).getResourceLocation();
        }

        this.addWeight(BOPClimates.COLD_SWAMP, 7);

        // mud
        this.addGenerator("mud", GeneratorStage.SAND_PASS2, (new GeneratorWaterside.Builder()).amountPerChunk(1).maxRadius(7).with(BOPBlocks.mud.getDefaultState()).create());
        
        // trees and logs
        GeneratorWeighted treeGenerator = new GeneratorWeighted(6);
        this.addGenerator("trees", GeneratorStage.TREE, treeGenerator);
        treeGenerator.add("oak_tree", 1, (new GeneratorTwigletTree.Builder()).leaves(BOPTrees.DEAD).minHeight(5).maxHeight(10).leafChance(0.3F, 0.9F).create());
        treeGenerator.add("dark_oak_tree", 7, (new GeneratorBigTree.Builder()).log(BlockPlanks.EnumType.DARK_OAK).leaves(BlockPlanks.EnumType.DARK_OAK).foliageHeight(2).maxHeight(12).create());
        treeGenerator.add("birch_twiglet", 1, (new GeneratorTwigletTree.Builder()).log(BlockPlanks.EnumType.BIRCH).leaves(BlockPlanks.EnumType.BIRCH).minHeight(2).maxHeight(4).leafChance(0.3F, 0.9F).create());
        
        // grasses
        GeneratorWeighted grassGenerator = new GeneratorWeighted(5.0F);
        this.addGenerator("grass", GeneratorStage.GRASS, grassGenerator);
        grassGenerator.add("shortgrass", 4, (new GeneratorGrass.Builder()).with(BOPPlants.SHORTGRASS).create());
        grassGenerator.add("mediumgrass", 2, (new GeneratorGrass.Builder()).with(BOPPlants.MEDIUMGRASS).create());        
        //grassGenerator.add("dampgrass", 2, (new GeneratorGrass.Builder()).with(BOPPlants.DAMPGRASS).create());
        grassGenerator.add("tallgrass", 2, (new GeneratorGrass.Builder()).with(BlockTallGrass.EnumType.GRASS).create());
        grassGenerator.add("doublegrass", 3, (new GeneratorDoubleFlora.Builder()).with(BlockDoublePlant.EnumPlantType.GRASS).create());
         
        // other plants
        this.addGenerator("cattail", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.25F).with(BOPPlants.CATTAIL).create());
        this.addGenerator("double_cattail", GeneratorStage.FLOWERS,(new GeneratorDoubleFlora.Builder()).amountPerChunk(0.25F).with(BlockBOPDoublePlant.DoublePlantType.TALL_CATTAIL).create());
        this.addGenerator("river_cane", GeneratorStage.FLOWERS,(new GeneratorColumns.Builder()).amountPerChunk(0.1F).generationAttempts(24).placeOn(BlockQueries.litFertileWaterside).with(BlockBOPPlant.paging.getVariantState(BOPPlants.RIVERCANE)).minHeight(1).maxHeight(3).create());
        this.addGenerator("koru", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.1F).with(BOPPlants.KORU).create());
        //this.addGenerator("shrubs", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(0.1F).with(BOPPlants.SHRUB).create());
        this.addGenerator("dead_leaf_piles", GeneratorStage.FLOWERS,(new GeneratorFlora.Builder()).amountPerChunk(1.5F).placeOn(BlockQueries.fertile).with(BOPPlants.DEADLEAFPILE).generationAttempts(64).create());
        
        // water plants
        this.addGenerator("water_reeds", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(15.0F).with(BOPPlants.REED).generationAttempts(32).create());
        this.addGenerator("lily", GeneratorStage.LILYPAD, (new GeneratorFlora.Builder()).amountPerChunk(0.25F).with(Blocks.WATERLILY.getDefaultState()).create());
        
        // shrooms
        this.addGenerator("flat_mushroom", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.1F).generationAttempts(16).with(BlockBOPMushroom.MushroomType.FLAT_MUSHROOM).create());
        this.addGenerator("toadstools", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.2F).generationAttempts(16).with(BlockBOPMushroom.MushroomType.TOADSTOOL).create());
        this.addGenerator("brown_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.4F).generationAttempts(16).with(Blocks.BROWN_MUSHROOM.getDefaultState()).create());
        this.addGenerator("red_mushrooms", GeneratorStage.SHROOM,(new GeneratorFlora.Builder()).amountPerChunk(0.4F).generationAttempts(16).with(Blocks.RED_MUSHROOM.getDefaultState()).create());
        
        // gem
        this.addGenerator("malachite", GeneratorStage.SAND, (new GeneratorOreSingle.Builder()).amountPerChunk(12).with(BOPGems.MALACHITE).create());
   
    }
    
    @Override
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        super.decorate(worldIn, rand, pos);

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, pos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FOSSIL))
        if (rand.nextInt(64) == 0)
        {
            (new WorldGenFossils()).generate(worldIn, rand, pos);
        }
    }
    
    @Override
    public int getGrassColorAtPos(BlockPos pos)
    {
        return getModdedBiomeGrassColor(0x866849);
    }

    @Override
    public int getFoliageColorAtPos(BlockPos pos)
    {
        return getModdedBiomeFoliageColor(0xB68542);
    }
}
