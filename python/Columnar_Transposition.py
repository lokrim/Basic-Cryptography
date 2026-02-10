import math

def encrypt(msg, key):
    """Encrypts using Columnar Transposition."""
    msg = msg.replace(" ", "")
    cipher = ""

    # Track key indices
    k_indx = 0
    msg_len = float(len(msg))
    msg_lst = list(msg)
    
    # Sort the key to determine column reading order
    # sorted(list(key)) gives alphabetical order
    key_lst = sorted(list(key))
    
    # Calculate number of rows
    col = len(key)
    row = int(math.ceil(msg_len / col))
    
    # Add padding characters ('_') to complete the grid
    fill_null = int((row * col) - msg_len)
    msg_lst.extend('_' * fill_null)
    
    # Create the matrix
    matrix = [msg_lst[i: i + col] for i in range(0, len(msg_lst), col)]

    # Read matrix column-by-column based on key order
    # We find the index of the current alphabetical key char in the original key
    # Then read that column
    for _ in range(col):
        curr_idx = key.find(key_lst[k_indx])
        
        # Determine the column from the matrix
        cipher += ''.join([row[curr_idx] for row in matrix])
        
        # Mark this key character as used so we don't pick it again (for duplicate letters)
        # We replace it in the temporary key string, not the original logic
        key_lst[k_indx] = None # Move to next sorted char
        # To handle duplicate letters in key, we need a smarter way, 
        # but for simple labs, we often assume unique keys or just mask the found index.
        # Here is a robust masking technique:
        key = key[:curr_idx] + '*' + key[curr_idx+1:]
        
        k_indx += 1
    
    return cipher

def decrypt(cipher, key):
    """Decrypts Columnar Transposition."""
    msg = ""

    # Calculate dimensions
    k_indx = 0
    msg_indx = 0
    msg_len = float(len(cipher))
    msg_lst = list(cipher)
    
    col = len(key)
    row = int(math.ceil(msg_len / col))
    
    # Determine the order of columns
    key_lst = sorted(list(key))
    
    # Create an empty matrix
    dec_cipher = []
    for _ in range(row):
        dec_cipher += [[None] * col]

    # Fill the matrix column by column
    for _ in range(col):
        curr_idx = key.find(key_lst[k_indx])
        
        for j in range(row):
            dec_cipher[j][curr_idx] = msg_lst[msg_indx]
            msg_indx += 1

        # Mask the key index used
        key = key[:curr_idx] + '*' + key[curr_idx+1:]
        k_indx += 1
    
    # Read the matrix row by row
    msg = ''.join(sum(dec_cipher, []))
    
    # Remove padding
    null_count = msg.count('_')
    if null_count > 0:
        return msg[: -null_count]
    return msg

# --- Main Execution ---
if __name__ == "__main__":
    msg = "DEFEND THE EAST WALL"
    key = "GERMAN"
    
    encrypted = encrypt(msg, key)
    decrypted = decrypt(encrypted, key)
    
    print(f"Original:  {msg}")
    print(f"Key:       {key}")
    print(f"Encrypted: {encrypted}")
    print(f"Decrypted: {decrypted}")