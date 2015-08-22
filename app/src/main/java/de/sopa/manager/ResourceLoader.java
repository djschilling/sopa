package de.sopa.manager;

import android.content.res.AssetManager;

import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;

import org.andengine.util.adt.io.in.IInputStreamOpener;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class ResourceLoader {

    private final TextureManager textureManager;
    private final AssetManager assetManager;
    private final FontManager fontManager;

    public ResourceLoader(TextureManager textureManager, AssetManager assetManager, FontManager fontManager) {

        this.textureManager = textureManager;
        this.assetManager = assetManager;
        this.fontManager = fontManager;
    }

    public TextureRegion getTexture(final String path) {

        try {
            ITexture texture = new BitmapTexture(textureManager, new IInputStreamOpener() {

                        @Override
                        public InputStream open() throws IOException {

                            return assetManager.open(path);
                        }
                    }, TextureOptions.BILINEAR);

            return TextureRegionFactory.extractFromTexture(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Map<Character, TextureRegion> getTileTextures() {

        Map<Character, TextureRegion> regionMap = new HashMap<>();
        regionMap.put('s', getTexture("scenes/game/bordersStart.png"));

        regionMap.put('f', getTexture("scenes/game/bordersFinish.png"));
        regionMap.put('o', getTexture("scenes/game/o.png"));
        regionMap.put('a', getTexture("scenes/game/a.png"));
        regionMap.put('u', getTexture("scenes/game/u.png"));
        regionMap.put('c', getTexture("scenes/game/c.png"));
        regionMap.put('e', getTexture("scenes/game/e.png"));
        regionMap.put('g', getTexture("scenes/game/g.png"));
        regionMap.put('i', getTexture("scenes/game/i.png"));
        regionMap.put('S', getTexture("scenes/game/bordersStart_filled.png"));
        regionMap.put('F', getTexture("scenes/game/bordersFinish_filled.png"));
        regionMap.put('O', getTexture("scenes/game/o.png"));
        regionMap.put('A', getTexture("scenes/game/a_filled.png"));
        regionMap.put('U', getTexture("scenes/game/u_filled.png"));
        regionMap.put('C', getTexture("scenes/game/c_filled.png"));
        regionMap.put('E', getTexture("scenes/game/e_filled.png"));
        regionMap.put('G', getTexture("scenes/game/g_filled.png"));
        regionMap.put('I', getTexture("scenes/game/i_filled.png"));

        return regionMap;
    }


    public IFont getFont(final String name, float size, int color, float strokeWidth, int strokeColor) {

        FontFactory.setAssetBasePath("fonts/");

        final ITexture mainFontTexture = new BitmapTextureAtlas(textureManager, 1024, 1024, TextureOptions.BILINEAR);
        IFont font = FontFactory.createStrokeFromAsset(fontManager, mainFontTexture, assetManager, name, size, true,
                color, strokeWidth, strokeColor);
        font.load();

        return font;
    }
}
