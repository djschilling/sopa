package de.sopa.manager;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.font.FontManager;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.io.in.IInputStreamOpener;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
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

    public Map<Character, TiledTextureRegion> getTileTextures() {
        Map<Character, TiledTextureRegion> regionMap = new HashMap<>();
        regionMap.put('s', getTiledTexture("scenes/game/bordersStart.png",400,200,2,1));
        regionMap.put('f', getTiledTexture("scenes/game/bordersFinish.png", 400, 200, 2, 1));
        regionMap.put('o', getTiledTexture("scenes/game/o.png", 400, 200, 2, 1));
        regionMap.put('a', getTiledTexture("scenes/game/a.png", 400, 200, 2, 1));
        regionMap.put('u', getTiledTexture("scenes/game/u.png", 400, 200, 2, 1));
        regionMap.put('c', getTiledTexture("scenes/game/c.png", 400, 200, 2, 1));
        regionMap.put('e', getTiledTexture("scenes/game/e.png", 400, 200, 2, 1));
        regionMap.put('g', getTiledTexture("scenes/game/g.png", 400, 200, 2, 1));
        regionMap.put('i', getTiledTexture("scenes/game/i.png", 400, 200, 2, 1));
        return regionMap;
    }

    public IFont getFont(final String name, float size, int color, float strokeWidth, int strokeColor) {
        FontFactory.setAssetBasePath("fonts/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, TextureOptions.BILINEAR);
        IFont font = FontFactory.createStrokeFromAsset(fontManager,
        mainFontTexture, assetManager, name, size , true, color, strokeWidth, strokeColor);
        font.load();
        return font;
    }

    public TiledTextureRegion getTiledTexture(String path, int width, int height, int tileColumns, int tileRows) {
        BitmapTextureAtlas texBanana = new BitmapTextureAtlas(textureManager, width, height, TextureOptions.BILINEAR);
        TiledTextureRegion regBanana = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(texBanana, assetManager, path, 0, 0, tileColumns, tileRows);
        return regBanana;
    }
}
