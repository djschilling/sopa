package de.sopa;

import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;

/**
 * David Schilling - davejs92@gmail.com
 */
public class TileResourceLoader {

    private final TextureManager textureManager;
    private final AssetManager assetManager;

    public TileResourceLoader(TextureManager textureManager, AssetManager assetManager) {
        this.textureManager = textureManager;
        this.assetManager = assetManager;
    }

    public TextureRegion getTexture(final String path){
        try {
            ITexture texture = new BitmapTexture(textureManager, new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return assetManager.open(path);
                }
            });
            texture.load();
            return TextureRegionFactory.extractFromTexture(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<Character, TextureRegion> getTileTextures() {
        Map<Character, TextureRegion> regionMap = new HashMap<>();
        regionMap.put('s', getTexture("tiles/s.png"));
        regionMap.put('f', getTexture("tiles/f.png"));
        regionMap.put('o', getTexture("tiles/o.png"));
        regionMap.put('r', getTexture("tiles/r.png"));
        regionMap.put('l', getTexture("tiles/l.png"));
        regionMap.put('a', getTexture("tiles/a.png"));
        regionMap.put('b', getTexture("tiles/b.png"));
        regionMap.put('u', getTexture("tiles/u.png"));
        regionMap.put('c', getTexture("tiles/c.png"));
        regionMap.put('d', getTexture("tiles/d.png"));
        regionMap.put('t', getTexture("tiles/t.png"));
        regionMap.put('e', getTexture("tiles/e.png"));
        regionMap.put('g', getTexture("tiles/g.png"));
        regionMap.put('h', getTexture("tiles/h.png"));
        regionMap.put('i', getTexture("tiles/i.png"));
        regionMap.put('j', getTexture("tiles/j.png"));
        regionMap.put('k', getTexture("tiles/k.png"));
        regionMap.put('m', getTexture("tiles/m.png"));
        return regionMap;
    }
}
