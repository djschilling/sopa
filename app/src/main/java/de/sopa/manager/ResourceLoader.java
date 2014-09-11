package de.sopa.manager;

import android.app.Activity;
import android.content.res.AssetManager;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.music.MusicManager;
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

/**
 * David Schilling - davejs92@gmail.com
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
        return getTexture(path, TextureOptions.DEFAULT);
    }

    public TextureRegion getTexture(final String path, TextureOptions textureOptions) {
        try {
            ITexture texture = new BitmapTexture(textureManager, new IInputStreamOpener() {
                @Override
                public InputStream open() throws IOException {
                    return assetManager.open(path);
                }
            }, textureOptions);
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
        regionMap.put('r', getTexture("scenes/game/r.png"));
        regionMap.put('l', getTexture("scenes/game/l.png"));
        regionMap.put('a', getTexture("scenes/game/a.png"));
        regionMap.put('b', getTexture("scenes/game/b.png"));
        regionMap.put('u', getTexture("scenes/game/u.png"));
        regionMap.put('c', getTexture("scenes/game/c.png"));
        regionMap.put('d', getTexture("scenes/game/d.png"));
        regionMap.put('t', getTexture("scenes/game/t.png"));
        regionMap.put('e', getTexture("scenes/game/e.png"));
        regionMap.put('g', getTexture("scenes/game/g.png"));
        regionMap.put('h', getTexture("scenes/game/h.png"));
        regionMap.put('i', getTexture("scenes/game/i.png"));
        regionMap.put('j', getTexture("scenes/game/j.png"));
        regionMap.put('k', getTexture("scenes/game/k.png"));
        regionMap.put('m', getTexture("scenes/game/m.png"));
        return regionMap;
    }

    public IFont getFont(final String name, TextureOptions textureOptions, float size, int color, float strokeWidth, int strokeColor) {
        FontFactory.setAssetBasePath("fonts/");
        final ITexture mainFontTexture = new BitmapTextureAtlas(textureManager, 512, 512, textureOptions);
        IFont font = FontFactory.createStrokeFromAsset(fontManager,
        mainFontTexture, assetManager, name, size , true, color, strokeWidth, strokeColor);
        font.load();
        return font;
    }

    public Music getMusic(String s, MusicManager musicManager, Activity activity) {
        Music music = null;
        try {
            music = MusicFactory.createMusicFromAsset(musicManager, activity, s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return music;
    }
}
