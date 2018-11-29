package chaining.block;

import chaining.utils.Modifier;
import chaining.utils.Utils;
import cryptoalgo.EncryptionAlgorithm;

public class PCBC<K> extends BlockCrypter<K> {

    private byte[] lastVector;

    private class EncryptionModifier implements Modifier {

        private byte[] plainText;

        @Override
        public byte[] firstModification(byte[] data, byte[] vector, int inputLength) {
            plainText = data;
            return Utils.xor(data, vector);
        }

        @Override
        public byte[] secondModification(byte[] data, byte[] vector, int inputLength) {
            lastVector = Utils.xor(data, plainText);
            return data;
        }
    }

    private class DecryptionModifier implements Modifier {

        private byte[] cipherText;

        @Override
        public byte[] firstModification(byte[] data, byte[] vector, int inputLength) {
            cipherText = data;
            return data;
        }

        @Override
        public byte[] secondModification(byte[] data, byte[] vector, int inputLength) {
            lastVector = Utils.xor(data, cipherText);
            return Utils.xor(data, vector);
        }
    }

    private final EncryptionModifier eMod = new EncryptionModifier();
    private final DecryptionModifier dMod = new DecryptionModifier();

    public PCBC(EncryptionAlgorithm<K> rootAlgorithm, int cryptableBlockSize) {
        super(rootAlgorithm, cryptableBlockSize);
    }

    @Override
    protected Modifier encryptionModifier() {
        return eMod;
    }

    @Override
    protected Modifier decryptionModifier() {
        return dMod;
    }

    @Override
    public byte[] getLastGeneratedVector() {
        return lastVector;
    }
}
