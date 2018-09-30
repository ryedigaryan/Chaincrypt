package chaining;

import chaining.helper.BlockCrypterDelegate;
import chaining.helper.BlockCrypterKeyProvider;
import chaining.helper.BlockCrypterVectorProvider;
import cryptoalgo.EncryptionAlgorithm;

public abstract class BlockCrypter<KeyType> extends EncryptionAlgorithm<KeyType> implements BlockCrypterKeyProvider {
    private BlockCrypterKeyProvider<KeyType> keyProvider;
    private BlockCrypterVectorProvider vectorProvider;
    //TODO: do not forget to call delegate.setNextBlockVector
    private BlockCrypterDelegate delegate;

    private int cryptableBlockSize;

    public BlockCrypter(EncryptionAlgorithm<KeyType> rootAlgorithm, int cryptableBlockSize) {
        this.cryptableBlockSize = cryptableBlockSize;
    }

    public int getCryptableBlockSize() {
        return cryptableBlockSize;
    }

    public void setKeyProvider(BlockCrypterKeyProvider<KeyType> keyProvider) {
        this.keyProvider = keyProvider;
    }

    public void setVectorProvider(BlockCrypterVectorProvider vectorProvider) {
        this.vectorProvider = vectorProvider;
    }

    public void setDelegate(BlockCrypterDelegate delegate) {
        this.delegate = delegate;
    }

    public void setCryptableBlockSize(int cryptableBlockSize) {
        this.cryptableBlockSize = cryptableBlockSize;
    }
}